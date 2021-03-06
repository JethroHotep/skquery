package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.lang.SkriptParser;
import com.w00tmast3r.skquery.api.Description;
import com.w00tmast3r.skquery.api.Examples;
import com.w00tmast3r.skquery.api.Name;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skquery.elements.effects.base.OptionsPragma;
import com.w00tmast3r.skquery.db.ScriptCredentials;

import java.io.File;

@Name("SQL Password Option")
@Description("Sets the script-wide database connection password. Required for an SQL connection.")
@Examples("script options:;->$ init com.mysql.jdbc.Driver;->$ db url jdbc:mysql://localhost:3306/skript;->$ db username admin;->$ db password heil_putin")
@Patterns("$ db password <.+>")
public class EffOptionSQLPassword extends OptionsPragma {
    @Override
    protected void register(File executingScript, SkriptParser.ParseResult parseResult) {
        ScriptCredentials.setPassword(executingScript, parseResult.regexes.get(0).group());
    }
}
