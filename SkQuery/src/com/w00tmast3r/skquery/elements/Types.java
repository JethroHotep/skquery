package com.w00tmast3r.skquery.elements;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.classes.Serializer;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.skript.registrations.Classes;
import ch.njol.yggdrasil.Fields;
import com.w00tmast3r.skquery.api.AbstractTask;
import com.w00tmast3r.skquery.skript.Dynamic;
import com.w00tmast3r.skquery.skript.LambdaCondition;
import com.w00tmast3r.skquery.skript.LambdaEffect;
import com.w00tmast3r.skquery.skript.Markup;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import java.io.NotSerializableException;
import java.io.StreamCorruptedException;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Types extends AbstractTask {

    private static final Pattern RGB_COLOR = Pattern.compile("([0-9]{1,3})\\s*,\\s*([0-9]{1,3})\\s*,\\s*([0-9]{1,3})");

    @Override
    public void run() {
        Classes.registerClass(new ClassInfo<>(FireworkEffect.class, "fireworkeffect")
                .parser(new Parser<FireworkEffect>() {
                    @Override
                    public FireworkEffect parse(String s, ParseContext parseContext) {
                        return null;
                    }

                    @Override
                    public boolean canParse(ParseContext context) {
                        return false;
                    }

                    @Override
                    public String toString(FireworkEffect fireworkEffect, int i) {
                        return fireworkEffect.toString();
                    }

                    @Override
                    public String toVariableNameString(FireworkEffect fireworkEffect) {
                        return fireworkEffect.toString();
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }
                }));

        Classes.registerClass(new ClassInfo<>(ResultSet.class, "queryresult")
                .parser(new Parser<ResultSet>() {
                    @Override
                    public ResultSet parse(String s, ParseContext parseContext) {
                        return null;
                    }

                    @Override
                    public boolean canParse(ParseContext context) {
                        return false;
                    }

                    @Override
                    public String toString(ResultSet resultSet, int i) {
                        return resultSet.toString();
                    }

                    @Override
                    public String toVariableNameString(ResultSet resultSet) {
                        return resultSet.toString();
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }
                }));

        Classes.registerClass(new ClassInfo<>(Markup.class, "markup")
                .parser(new Parser<Markup>() {
                    @Override
                    public Markup parse(String s, ParseContext parseContext) {
                        if (s.charAt(0) == '`' && s.charAt(s.length() - 1) == '`') {
                            return new Markup(s.substring(1, s.length() - 1));
                        } else {
                            return null;
                        }
                    }

                    @Override
                    public boolean canParse(ParseContext context) {
                        return true;
                    }

                    @Override
                    public String toString(Markup markup, int i) {
                        return markup.toString();
                    }

                    @Override
                    public String toVariableNameString(Markup markup) {
                        return markup.toString();
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }
                })
                .serializer(new Serializer<Markup>() {
                    @Override
                    public Fields serialize(Markup markup) throws NotSerializableException {
                        Fields f = new Fields();
                        f.putObject("src", markup.toString());
                        return f;
                    }

                    @Override
                    public void deserialize(Markup markup, Fields fieldContexts) throws StreamCorruptedException, NotSerializableException {
                        assert false;
                    }

                    @Override
                    protected Markup deserialize(Fields fields) throws StreamCorruptedException, NotSerializableException {
                        return new Markup((String) fields.getObject("src"));
                    }

                    @Override
                    public boolean mustSyncDeserialization() {
                        return true;
                    }

                    @Override
                    public boolean canBeInstantiated(Class<? extends Markup> aClass) {
                        return false;
                    }

					@Override
					protected boolean canBeInstantiated() {
						return false;
					}
                }));

        Classes.registerClass(new ClassInfo<>(LambdaCondition.class, "predicate")
                .parser(new Parser<LambdaCondition>() {
                    @Override
                    public LambdaCondition parse(String s, ParseContext parseContext) {
                        if (s.length() > 2 && s.charAt(0) == '[' && s.charAt(s.length() - 1) == ']') {
                            Condition e = (Condition) Condition.parse(s.substring(1, s.length() - 1), null);
                            if (e == null) {
                                Skript.error(s + " is not a valid lambda statement.", ErrorQuality.SEMANTIC_ERROR);
                            } else {
                                return new LambdaCondition(e);
                            }
                        }
                        return null;
                    }

                    @Override
                    public boolean canParse(ParseContext context) {
                        return true;
                    }

                    @Override
                    public String toString(LambdaCondition lambdaCondition, int i) {
                        return lambdaCondition.toString();
                    }

                    @Override
                    public String toVariableNameString(LambdaCondition lambdaCondition) {
                        return lambdaCondition.toString();
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }
                }));

        Classes.registerClass(new ClassInfo<>(LambdaEffect.class, "lambda")
                .parser(new Parser<LambdaEffect>() {
                    @Override
                    public LambdaEffect parse(String s, ParseContext parseContext) {
                        if (s.length() > 3 && s.charAt(0) == '[' && s.charAt(s.length() - 1) == ']') {
                            if ("void".equals(s.substring(1, s.length() - 1))) return new LambdaEffect(true);
                            Effect e = Effect.parse(s.substring(1, s.length() - 1), null);
                            if (e == null) {
                                Skript.error(s + " is not a valid lambda statement.", ErrorQuality.SEMANTIC_ERROR);
                            } else {
                                return new LambdaEffect(e);
                            }
                        }
                        return null;
                    }

                    @Override
                    public boolean canParse(ParseContext context) {
                        return true;
                    }

                    @Override
                    public String toString(LambdaEffect lambdaEffect, int i) {
                        return lambdaEffect.toString();
                    }

                    @Override
                    public String toVariableNameString(LambdaEffect lambdaEffect) {
                        return lambdaEffect.toString();
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }
                }));


        if (Skript.classExists("org.bukkit.Color")) {
	        Classes.registerClass(new ClassInfo<>(Color.class, "rgbcolor")
	                .parser(new Parser<Color>() {
	                    @Override
	                    public Color parse(final String s, final ParseContext context) {
	                        Matcher m = RGB_COLOR.matcher(s);
	                        if (m.matches()) {
	                            int r = Integer.parseInt(m.group(1));
	                            int g = Integer.parseInt(m.group(2));
	                            int b = Integer.parseInt(m.group(3));
	                            if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) return null;
	                            return Color.fromRGB(r, g, b);
	                        }
	                        ch.njol.skript.util.Color c = ch.njol.skript.util.Color.byName(s);
	                        if (c == null) return null;
	                        return c.getBukkitColor();
	                    }
	
	                    @Override
	                    public String toString(final Color c, final int flags) {
	                        return c.toString();
	                    }
	
	                    @Override
	                    public String toVariableNameString(final Color o) {
	                        return o.toString();
	                    }
	
	                    @Override
	                    public String getVariableNamePattern() {
	                        return ".+";
	                    }
	                })
	                .serializer(new Serializer<Color>() {
	                    @Override
	                    public Fields serialize(Color o) throws NotSerializableException {
	                        Fields f = new Fields();
	                        f.putPrimitive("rgb", o.asRGB());
	                        return f;
	                    }
	
	                    @Override
	                    public void deserialize(Color o, Fields f) throws StreamCorruptedException, NotSerializableException {
	                        assert false;
	                    }
	
	                    @Override
	                    protected Color deserialize(Fields fields) throws StreamCorruptedException, NotSerializableException {
	                        return Color.fromRGB(fields.getPrimitive("rgb", int.class));
	                    }
	
	                    @Override
	                    public boolean mustSyncDeserialization() {
	                        return true;
	                    }
	
	                    @Override
	                    public boolean canBeInstantiated(Class<? extends Color> c) {
	                        return false;
	                    }
	
						@Override
						protected boolean canBeInstantiated() {
							return false;
						}
	                }));
        }

        Classes.registerClass(new ClassInfo<>(Dynamic.class, "dynamic")
                .parser(new Parser<Dynamic>() {
                    @Override
                    public Dynamic parse(final String s, final ParseContext context) {
                        return null;
                    }

                    @Override
                    public String toString(final Dynamic d, final int flags) {
                        return d.toString();
                    }

                    @Override
                    public String toVariableNameString(final Dynamic o) {
                        return o.toString();
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }
                })
                .serializer(new Serializer<Dynamic>() {
                    @Override
                    public Fields serialize(Dynamic o) throws NotSerializableException {
                        Fields f = new Fields();
                        return f;
                    }

                    @Override
                    public void deserialize(Dynamic o, Fields f) throws StreamCorruptedException, NotSerializableException {
                        assert false;
                    }

                    @Override
                    protected Dynamic deserialize(Fields fields) throws StreamCorruptedException, NotSerializableException {
                        return null;
                    }

                    @Override
                    public boolean mustSyncDeserialization() {
                        return true;
                    }

                    @Override
                    public boolean canBeInstantiated(Class<? extends Dynamic> c) {
                        return false;
                }

					@Override
					protected boolean canBeInstantiated() {
						return false;
					}
                }));
    }
}
