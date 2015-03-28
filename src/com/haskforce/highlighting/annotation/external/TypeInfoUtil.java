package com.haskforce.highlighting.annotation.external;

import com.haskforce.settings.ToolKey;
import com.haskforce.utils.ExecUtil;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.VisualPosition;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiUtilBase;

/**
 * This class will return the type info of the type under cursor or the selection. It seems to be necessary
 * to correct the positions under cursor. IntelliJ treats the 'origin' position of a file as (0,0). GhcMod treats
 * files as (1,1) based.
 * Effectively, this means that if IntelliJ reports the selection as being (2,1), for GhcMod
 * it should be corrected to (3,2).
 * <p/>
 * Next to this offset correction, this class just calls GhcMod and returns the type info. In case no type info can be found
 * it will report the error encountered instead of the type info.
 */
public class TypeInfoUtil {
    public static String getTypeInfo(Project project) {
        FileEditorManager fileEditorManager = FileEditorManager.getInstance(
                project);
        if (fileEditorManager == null){
            return null;
        }
        Editor textEditor = fileEditorManager.getSelectedTextEditor();
        if (textEditor == null){
            return null;
        }

        VisualPosition blockStart = correctFor0BasedVS1Based(textEditor.getSelectionModel().getSelectionStartPosition());
        VisualPosition blockEnd = correctFor0BasedVS1Based(textEditor.getSelectionModel().getSelectionEndPosition());
        if (blockStart == null || blockEnd == null){
            return null;
        }

        PsiFile psiFileInEditor = PsiUtilBase.getPsiFileInEditor(textEditor, project);
        if(psiFileInEditor == null){
            return "no psi file found";
        }
        VirtualFile projectFile = psiFileInEditor.getVirtualFile();
        if (projectFile == null){
            return "project file is null";
        }

        return getTypeInfo(project, blockStart, blockEnd, projectFile);
    }

    public static String getTypeInfo(Project project, VisualPosition blockStart, VisualPosition blockEnd, VirtualFile projectFile) {
        final String canonicalPath = projectFile.getCanonicalPath();
        if (canonicalPath == null){
            return "canonical path is null";
        }
        final String workDir = ExecUtil.guessWorkDir(project, projectFile);
        if (ToolKey.GHC_MODI_KEY.getPath(project) != null) {
            final Module module = ModuleUtilCore.findModuleForFile(projectFile, project);
            if (module != null) {
                GhcModi ghcModi = module.getComponent(GhcModi.class);
                if (ghcModi != null) {
                    return GhcModi.getFutureType(project, ghcModi.type(canonicalPath,
                            blockStart,blockEnd));

                } else {
                    return "ghcModi is not configured";
                }
            } else {
                return "didn't find module";
            }
        } else {
            return GhcMod.type(project, workDir, canonicalPath, blockStart, blockEnd);
        }
    }

    public static VisualPosition correctFor0BasedVS1Based(VisualPosition pos0Based) {
        if (pos0Based == null){
            return null;
        }
        return new VisualPosition(pos0Based.line+1,pos0Based.column+1);
    }
}
