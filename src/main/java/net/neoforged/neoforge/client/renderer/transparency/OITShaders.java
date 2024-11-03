package net.neoforged.neoforge.client.renderer.transparency;

public final class OITShaders
{

    private OITShaders()
    {
        throw new IllegalStateException("Can not instantiate an instance of: ForgeTransparencyShaders. This is a utility class");
    }

    public static final String FRAGMENT_SHADER_VERSION_HEADER = """
            #version 150 core
            #extension GL_ARB_explicit_attrib_location : require
            """;

    public static final String FRAGMENT_SHADER_PREFIX = """
            layout (location = 0) out vec4 accum;
            layout (location = 1) out float reveal;
            """;

    public static final String FRAGMENT_SHADER_SUFFIX = """
            float weight(vec4 color, float z) {
                float depthZ = gl_FragCoord.z;
                float alphaFactor = max(pow(10, -2), 3000 * pow((1-depthZ), 3));
                return color.a * alphaFactor;
            }
            
            void main() {
                mainShader();
            
                if (!oitEnabled) {
                    accum = %OUT_COLOR%;
                    return;
                }
            
                vec4 color = %OUT_COLOR%;
            
                float weight = weight(color, %Z_VALUE%);
            
                accum = vec4(color.rgb * weight, color.a);
                reveal = color.a * weight;
            }
            """;
}
