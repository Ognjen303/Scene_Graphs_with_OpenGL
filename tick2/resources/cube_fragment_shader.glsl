#version 140

const float pi = 3.141592653589793;

const float I_a = 1; 				   // Ambient illumination intensity


const vec3 pointLight = vec3(4); // point light source position in wc
const float I_i = 40; // point light source intensity
const vec3 C_spec = vec3(1.0, 1.0, 1.0); // light colour


// Diffusion model constants
const float k_d = 0.8;
const float k_s = 1.2;
const float alpha = 10.0;
const float reflectivity = 0.3; 

// colour of cube define initially to be red
// const vec3 C_diff = vec3(1.0, 0.0, 0.0);




in vec3 wc_frag_normal;        	// fragment normal in world coordinates (wc_)
in vec2 frag_texcoord;			// texture UV coordinates
in vec3 wc_frag_pos;			// fragment position in world coordinates

out vec3 color;			        // pixel colour

uniform sampler2D tex;  		  // 2D texture sampler
uniform samplerCube skybox;		  // Cubemap texture used for reflections
uniform vec3 wc_camera_position;  // Position of the camera in world coordinates

// Tone mapping and display encoding combined
vec3 tonemap(vec3 linearRGB)
{
	// convert physical light signal to digital signal
    float L_white = 0.7; // Controls the brightness of the image, a = 0.7

    float inverseGamma = 1./2.2;
    return pow(linearRGB/L_white, vec3(inverseGamma)); // Display encoding - a gamma
}



void main()
{
	// TODO: Sample the texture and replace diffuse surface colour (C_texel) with texel value
	// texel je pixel na UV texture 
	vec3 C_texel = texture(tex, frag_texcoord).rgb; // This samples the colour of texture
	
	
	
	vec3 linear_color = vec3(0, 0, 0);
	
	vec3 ambient = C_texel * I_a;
	vec3 L = normalize(pointLight - wc_frag_pos);
	
	vec3 diffuse = C_texel * k_d * I_i * max(0, dot(wc_frag_normal, L));
	vec3 R = normalize(reflect(-L, wc_frag_normal));
	vec3 V = normalize(wc_camera_position - wc_frag_pos);
	
	float distanceToLight = length(pointLight - wc_frag_pos);
	
	vec3 specular = C_spec * k_s * I_i / (4 * pow(distanceToLight, 2) * pi) * max(0, pow(dot(R, V), alpha));
	
	// I found it convenient to multiply this by 0.6 to get desired effect
	vec3 skybox_color = 0.6 * texture(skybox, R).rgb;
	
	// TODO: Calculate colour using Phong illumination model
	linear_color = ambient + diffuse + specular + skybox_color;

	color = tonemap(linear_color);
}

