package com.haskforce.parsing.jsonParser;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.haskforce.parsing.srcExtsDatatypes.*;

import java.lang.reflect.Type;

/**
 * Deserializes expressions.
 */
public class ExpTopTypeDeserializer implements JsonDeserializer<ExpTopType> {
    @Override
    public ExpTopType deserialize(JsonElement jsonElement, Type type,
                                     JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject objType = jsonElement.getAsJsonObject();
        JsonArray stuff;
        if ((stuff = objType.getAsJsonArray("Var")) != null) {
            Var rhs = new Var();
            rhs.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            rhs.qName = jsonDeserializationContext.deserialize(stuff.get(1), QNameTopType.class);
            return rhs;
        } else if ((stuff = objType.getAsJsonArray("IPVar")) != null) { // TODO: Test.
            IPVar ipVar = new IPVar();
            ipVar.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            ipVar.ipName = jsonDeserializationContext.deserialize(stuff.get(1), IPNameTopType.class);
            return ipVar;
        } else if ((stuff = objType.getAsJsonArray("Con")) != null) {
            Con con = new Con();
            con.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            con.qName = jsonDeserializationContext.deserialize(stuff.get(1), QNameTopType.class);
            return con;
        } else if ((stuff = objType.getAsJsonArray("Lit")) != null) {
            Lit lit = new Lit();
            lit.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            lit.literal = jsonDeserializationContext.deserialize(stuff.get(1), LiteralTopType.class);
            return lit;
        } else if ((stuff = objType.getAsJsonArray("InfixApp")) != null) {
            InfixApp infixApp = new InfixApp();
            infixApp.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            infixApp.e1 = jsonDeserializationContext.deserialize(stuff.get(1), ExpTopType.class);
            infixApp.qop = jsonDeserializationContext.deserialize(stuff.get(2), QOpTopType.class);
            infixApp.e2 = jsonDeserializationContext.deserialize(stuff.get(3), ExpTopType.class);
            return infixApp;
        } else if ((stuff = objType.getAsJsonArray("App")) != null) {
            App app = new App();
            app.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            app.e1 = jsonDeserializationContext.deserialize(stuff.get(1), ExpTopType.class);
            app.e2 = jsonDeserializationContext.deserialize(stuff.get(2), ExpTopType.class);
            return app;
        } else if ((stuff = objType.getAsJsonArray("NegApp")) != null) { // TODO: Test.
            NegApp negApp = new NegApp();
            negApp.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            negApp.e1 = jsonDeserializationContext.deserialize(stuff.get(1), ExpTopType.class);
            return negApp;
        } else if ((stuff = objType.getAsJsonArray("Lambda")) != null) {
            Lambda lambda = new Lambda();
            lambda.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            lambda.pats = jsonDeserializationContext.deserialize(stuff.get(1), PatTopType[].class);
            lambda.exp = jsonDeserializationContext.deserialize(stuff.get(2), ExpTopType.class);
            return lambda;
        } else if ((stuff = objType.getAsJsonArray("Let")) != null) { // TODO: Test.
            Let let = new Let();
            let.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            let.binds = jsonDeserializationContext.deserialize(stuff.get(1), BindsTopType.class);
            let.exp = jsonDeserializationContext.deserialize(stuff.get(2), ExpTopType.class);
            return let;
        } else if ((stuff = objType.getAsJsonArray("If")) != null) { // TODO: Test.
            If anIf = new If();
            anIf.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            anIf.cond = jsonDeserializationContext.deserialize(stuff.get(1), ExpTopType.class);
            anIf.t = jsonDeserializationContext.deserialize(stuff.get(2), ExpTopType.class);
            anIf.f = jsonDeserializationContext.deserialize(stuff.get(3), ExpTopType.class);
            return anIf;
        } else if ((stuff = objType.getAsJsonArray("MultiIf")) != null) { // TODO: Test.
            MultiIf multiIf = new MultiIf();
            multiIf.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            multiIf.alts = jsonDeserializationContext.deserialize(stuff.get(1), IfAlt[].class);
            return multiIf;
        } else if ((stuff = objType.getAsJsonArray("Case")) != null) {
            Case aCase = new Case();
            aCase.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            aCase.scrutinee= jsonDeserializationContext.deserialize(stuff.get(1), ExpTopType.class);
            aCase.alts = jsonDeserializationContext.deserialize(stuff.get(2), Alt[].class);
            return aCase;
        } else if ((stuff = objType.getAsJsonArray("Do")) != null) { // TODO: Test.
            Do aDo = new Do();
            aDo.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            aDo.stmts = jsonDeserializationContext.deserialize(stuff.get(1), StmtTopType[].class);
            return aDo;
        } else if ((stuff = objType.getAsJsonArray("MDo")) != null) { // TODO: Test.
            MDo mDo = new MDo();
            mDo.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            mDo.stmts = jsonDeserializationContext.deserialize(stuff.get(1), StmtTopType[].class);
            return mDo;
        } else if ((stuff = objType.getAsJsonArray("Tuple")) != null) {
            Tuple tuple = new Tuple();
            tuple.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            tuple.boxed = jsonDeserializationContext.deserialize(stuff.get(1), BoxedTopType.class);
            tuple.exps = jsonDeserializationContext.deserialize(stuff.get(2), ExpTopType[].class);
            return tuple;
        } else if ((stuff = objType.getAsJsonArray("TupleSection")) != null) { // TODO: Test.
            TupleSection tupleSection = new TupleSection();
            tupleSection.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            tupleSection.boxed = jsonDeserializationContext.deserialize(stuff.get(1), BoxedTopType.class);
            tupleSection.expMaybes = jsonDeserializationContext.deserialize(stuff.get(2), ExpTopType[].class);
            return tupleSection;
        } else if ((stuff = objType.getAsJsonArray("List")) != null) {
            List list = new List();
            list.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            list.exps = jsonDeserializationContext.deserialize(stuff.get(1), ExpTopType[].class);
            return list;
        } else if ((stuff = objType.getAsJsonArray("Paren")) != null) {
            Paren paren = new Paren();
            paren.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            paren.exp = jsonDeserializationContext.deserialize(stuff.get(1), ExpTopType.class);
            return paren;
        } else if ((stuff = objType.getAsJsonArray("LeftSection")) != null) { // TODO: Test.
            LeftSection leftSection = new LeftSection();
            leftSection.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            leftSection.exp = jsonDeserializationContext.deserialize(stuff.get(1), ExpTopType.class);
            leftSection.qop = jsonDeserializationContext.deserialize(stuff.get(2), QOpTopType.class);
            return leftSection;
        } else if ((stuff = objType.getAsJsonArray("RightSection")) != null) {
            RightSection rightSection = new RightSection();
            rightSection.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            rightSection.qop = jsonDeserializationContext.deserialize(stuff.get(1), QOpTopType.class);
            rightSection.exp = jsonDeserializationContext.deserialize(stuff.get(2), ExpTopType.class);
            return rightSection;
        } else if ((stuff = objType.getAsJsonArray("RecConstr")) != null) { // TODO: Test.
            RecConstr recConstr = new RecConstr();
            recConstr.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            recConstr.qName = jsonDeserializationContext.deserialize(stuff.get(1), QNameTopType.class);
            recConstr.fieldUpdates = jsonDeserializationContext.deserialize(stuff.get(2), FieldUpdateTopType[].class);
            return recConstr;
        } else if ((stuff = objType.getAsJsonArray("RecUpdate")) != null) { // TODO: Test.
            RecUpdate recUpdate = new RecUpdate();
            recUpdate.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            recUpdate.exp = jsonDeserializationContext.deserialize(stuff.get(1), ExpTopType.class);
            recUpdate.fieldUpdates = jsonDeserializationContext.deserialize(stuff.get(2), FieldUpdateTopType[].class);
            return recUpdate;
        } else if ((stuff = objType.getAsJsonArray("EnumFrom")) != null) { // TODO: Test.
            EnumFrom enumFrom = new EnumFrom();
            enumFrom.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            enumFrom.exp = jsonDeserializationContext.deserialize(stuff.get(1), ExpTopType.class);
            return enumFrom;
        } else if ((stuff = objType.getAsJsonArray("EnumFromTo")) != null) { // TODO: Test.
            EnumFromTo enumFromTo = new EnumFromTo();
            enumFromTo.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            enumFromTo.from = jsonDeserializationContext.deserialize(stuff.get(1), ExpTopType.class);
            enumFromTo.to = jsonDeserializationContext.deserialize(stuff.get(2), ExpTopType.class);
            return enumFromTo;
        } else if ((stuff = objType.getAsJsonArray("EnumFromThen")) != null) { // TODO: Test.
            EnumFromThen enumFromThen = new EnumFromThen();
            enumFromThen.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            enumFromThen.from = jsonDeserializationContext.deserialize(stuff.get(1), ExpTopType.class);
            enumFromThen.step = jsonDeserializationContext.deserialize(stuff.get(2), ExpTopType.class);
            return enumFromThen;
        } else if ((stuff = objType.getAsJsonArray("EnumFromThenTo")) != null) { // TODO: Test.
            EnumFromThenTo enumFromThenTo = new EnumFromThenTo();
            enumFromThenTo.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            enumFromThenTo.from = jsonDeserializationContext.deserialize(stuff.get(1), ExpTopType.class);
            enumFromThenTo.step = jsonDeserializationContext.deserialize(stuff.get(2), ExpTopType.class);
            enumFromThenTo.to = jsonDeserializationContext.deserialize(stuff.get(3), ExpTopType.class);
            return enumFromThenTo;
        } else if ((stuff = objType.getAsJsonArray("ListComp")) != null) { // TODO: Test.
            ListComp listComp = new ListComp();
            listComp.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            listComp.exp = jsonDeserializationContext.deserialize(stuff.get(1), ExpTopType.class);
            listComp.qualStmts = jsonDeserializationContext.deserialize(stuff.get(2), QualStmtTopType[].class);
            return listComp;
        } else if ((stuff = objType.getAsJsonArray("ParComp")) != null) { // TODO: Test.
            ListComp listComp = new ListComp();
            listComp.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            listComp.exp = jsonDeserializationContext.deserialize(stuff.get(1), ExpTopType.class);
            listComp.qualStmts = jsonDeserializationContext.deserialize(stuff.get(2), QualStmtTopType[][].class);
            return listComp;
        } else if ((stuff = objType.getAsJsonArray("ExpTypeSig")) != null) {
            ExpTypeSig expTypeSig = new ExpTypeSig();
            expTypeSig.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            expTypeSig.exp= jsonDeserializationContext.deserialize(stuff.get(1), ExpTopType.class);
            expTypeSig.type = jsonDeserializationContext.deserialize(stuff.get(2), TypeTopType.class);
            return expTypeSig;
        } else if ((stuff = objType.getAsJsonArray("VarQuote")) != null) { // TODO: Test.
            VarQuote varQuote = new VarQuote();
            varQuote.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            varQuote.qName = jsonDeserializationContext.deserialize(stuff.get(1), QNameTopType.class);
            return varQuote;
        } else if ((stuff = objType.getAsJsonArray("TypQuote")) != null) { // TODO: Test.
            TypQuote typQuote = new TypQuote();
            typQuote.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            typQuote.qName = jsonDeserializationContext.deserialize(stuff.get(1), QNameTopType.class);
            return typQuote;
        } else if ((stuff = objType.getAsJsonArray("BracketExp")) != null) { // TODO: Test.
            BracketExp bracketExp = new BracketExp();
            bracketExp.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            bracketExp.bracket = jsonDeserializationContext.deserialize(stuff.get(1), BracketTopType.class);
            return bracketExp;
        } else if ((stuff = objType.getAsJsonArray("SpliceExp")) != null) { // TODO: Test.
            SpliceExp spliceExp = new SpliceExp();
            spliceExp.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            spliceExp.splice = jsonDeserializationContext.deserialize(stuff.get(1), SpliceTopType.class);
            return spliceExp;
        } else if ((stuff = objType.getAsJsonArray("QuasiQuote")) != null) {
            QuasiQuote quasiQuote = new QuasiQuote();
            quasiQuote.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            quasiQuote.s1 = jsonDeserializationContext.deserialize(stuff.get(1), String.class);
            quasiQuote.s2 = jsonDeserializationContext.deserialize(stuff.get(2), String.class);
            return quasiQuote;
        } // TODO: Add Hsx-types.
        else if ((stuff = objType.getAsJsonArray("CorePragma")) != null) { // TODO: Test.
            CorePragma corePragma = new CorePragma();
            corePragma.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            corePragma.s = jsonDeserializationContext.deserialize(stuff.get(1), String.class);
            corePragma.exp = jsonDeserializationContext.deserialize(stuff.get(2), ExpTopType.class);
            return corePragma;
        } else if ((stuff = objType.getAsJsonArray("SCCPragma")) != null) { // TODO: Test.
            SCCPragma sccPragma = new SCCPragma();
            sccPragma.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            sccPragma.s = jsonDeserializationContext.deserialize(stuff.get(1), String.class);
            sccPragma.exp = jsonDeserializationContext.deserialize(stuff.get(2), ExpTopType.class);
            return sccPragma;
        } else if ((stuff = objType.getAsJsonArray("GenPragma")) != null) { // TODO: Test.
            GenPragma genPragma = new GenPragma();
            genPragma.srcInfoSpan = jsonDeserializationContext.deserialize(stuff.get(0), SrcInfoSpan.class);
            genPragma.s = jsonDeserializationContext.deserialize(stuff.get(1), String.class);
            throw new JsonParseException("GenPragma currently  not handled: " + objType.toString());
            // return genPragma;
        }
        // TODO: Rest of ExpTopType
        throw new JsonParseException("Unexpected JSON object type: " + objType.toString());
    }
}