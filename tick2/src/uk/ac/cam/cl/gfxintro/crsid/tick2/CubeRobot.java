package uk.ac.cam.cl.gfxintro.crsid.tick2;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glGetError;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;
import java.nio.FloatBuffer;;

public class CubeRobot {
	// body constants
	private static final float scaleBodyX = 1.0f;
	private static final float scaleBodyY = 2.0f;
	private static final float scaleBodyZ = scaleBodyX;
	
	// arm constants
	private static final float scaleArmX = 0.3f;
	private static final float scaleArmY = 2.0f;
	private static final float scaleArmZ = scaleArmX;
	private static final float angleArm = (float)Math.toRadians(-15.0f); // 18 degrees
	
	// leg constants
	private static final float scaleLegX = 0.35f;
	private static final float scaleLegY = 1.7f;
	private static final float scaleLegZ = scaleLegX;
	
	// head constants
	private static final float scaleHeadX = 0.5f;
	private static final float scaleHeadY = scaleHeadX;
	private static final float scaleHeadZ = scaleHeadX;
	
	
	abstract class RobotPart {
		public RobotPart() {}
		
		public Mesh mesh;
		public ShaderProgram shader;
		public Texture texture;
		public Matrix4f transform;
		public abstract void executeInitialTransformation();
		public ArrayList<RobotPart> subParts;
	}
	
	// Components of this CubeRobot
	
	// Component 1: Body
	private RobotPart body = new RobotPart() {
		@Override
		public void executeInitialTransformation() {
			body.transform.scale(scaleBodyX, scaleBodyY, scaleBodyZ);
		}
	};
	
	
	
	
	// CubeRobot represents a graphical object that contains the meshes, shaders,
	// textures and their relative transformations required to draw an object that
	// looks like a robot.
	
    // Filenames for vertex and fragment shader source code
    private final static String VSHADER_FN = "resources/cube_vertex_shader.glsl";
    private final static String FSHADER_FN = "resources/cube_fragment_shader.glsl";
    
    // Reference to skybox of the scene
    public SkyBox skybox;
    
    // Components of this CubeRobot
    
    // Component 1 : Body
	/*private Mesh body_mesh;				// Mesh of the body
	private ShaderProgram body_shader;	// Shader to colour the body mesh
	private Texture body_texture;		// Texture image to be used by the body shader
	private Matrix4f M_body;	// Transformation matrix of the body object
	private Matrix4f E_body;*/

    
    private Matrix4f T_body;
    
    
	// TODO: Add Component 2: Right Arm
    // Component 2: Right Arm
 	private RobotPart rightArm = new RobotPart() {
 		@Override
 		public void executeInitialTransformation() {
 			rightArm.transform.translate(scaleBodyX + 0.4f, scaleBodyY - 0.2f, 0.0f)
 	   		   				  .translate(-scaleBodyY * (float)Math.sin(angleArm), -scaleBodyY * (float)Math.cos(angleArm), 0.0f)
 	   		   				  .rotateZ(-angleArm)
 	   		   				  .scale(scaleArmX, scaleArmY, scaleArmZ);
 		}
 	};
 	
 	// Component 3: Left Arm
 	private RobotPart leftArm = new RobotPart() {
 		@Override
 		public void executeInitialTransformation() {
 			leftArm.transform.translate(- scaleBodyX - 0.4f, scaleBodyY - 0.2f, 0.0f)
 				  			 .translate(scaleBodyY * (float)Math.sin(angleArm), -scaleBodyY * (float)Math.cos(angleArm), 0.0f)
 				  			 .rotateZ(angleArm)
 				  			 .scale(scaleArmX, scaleArmY, scaleArmZ);
 		}
 		
 	};
 	
 	// Component 4: Right Leg
 	private RobotPart rightLeg = new RobotPart() {
 		@Override
 		public void executeInitialTransformation() {
 			rightLeg.transform.translate(scaleBodyX - scaleLegX, -scaleBodyY - scaleLegY, 0.0f)
 							  .scale(scaleLegX, scaleLegY, scaleLegZ);
 		}
 	};
 	
 	// Component 5: Left Leg
 	private RobotPart leftLeg = new RobotPart() {
 		@Override
 		public void executeInitialTransformation() {
 			leftLeg.transform.translate(-scaleBodyX + scaleLegX, -scaleBodyY - scaleLegY, 0.0f)
 							 .scale(scaleLegX, scaleLegY, scaleLegZ);
 		}
 	};
 	
 	// Component 6: Head
 	private RobotPart head = new RobotPart() {
 		@Override
 		public void executeInitialTransformation() {
 			head.transform.translate(0.0f, scaleBodyY + scaleHeadY, 0.0f)
 						  .scale(scaleHeadX, scaleHeadY, scaleHeadZ);
 		}
 	};
 	
 	
 	private ArrayList<RobotPart> parts = new ArrayList<>(Arrays.asList(body, rightArm, leftArm, rightLeg, leftLeg, head)) {};
 	
 	/*
	private Mesh right_arm_mesh;
	private ShaderProgram right_arm_shader;
	private Texture right_arm_texture;
	private Matrix4f M_right_arm;
	private Matrix4f T_right_arm;
	private Matrix4f E_right_arm;
	*/
 	
 	
	// Complete rest of the robot
	
/**
 *  Constructor
 *  Initialize all the CubeRobot components
 */
	public CubeRobot() {
		// Create body node
		
		
		
		
		
		// Initialise Geometry, Body of robot is this cube mesh
		/*body_mesh = new CubeMesh(); 
		
		// Initialise Shader
		body_shader = new ShaderProgram(new Shader(GL_VERTEX_SHADER, VSHADER_FN), new Shader(GL_FRAGMENT_SHADER, FSHADER_FN), "colour");
		// Tell vertex shader where it can find vertex positions. 3 is the dimensionality of vertex position
		// The prefix "oc_" means object coordinates
		body_shader.bindDataToShader("oc_position", body_mesh.vertex_handle, 3);
		// Tell vertex shader where it can find vertex normals. 3 is the dimensionality of vertex normals
		body_shader.bindDataToShader("oc_normal", body_mesh.normal_handle, 3);
		// Tell vertex shader where it can find texture coordinates. 2 is the dimensionality of texture coordinates
		body_shader.bindDataToShader("texcoord", body_mesh.tex_handle, 2);
		
		// Initialise Texturing, a .png image
		// can be used later by the shader to colour the mesh
		body_texture = new Texture(); 
		body_texture.load("resources/cubemap.png"); */
		
		// Build Transformation Matrix
		// instructs shader on how to translate rotate or scale the object
		// Basis of robot body. Known as model matrix, 
		// which is uploaded to vertex shader, along with projection matrix
		// M_body = new Matrix4f(); 
		// E_body = new Matrix4f();
		
		// E_body.scale(1.0f, 2.0f, 1.0f);
		
		// TODO: Scale the body transformation matrix
		for (RobotPart rp : parts) {
			rp.mesh = new CubeMesh();
			// Tell vertex shader where it can find vertex positions. 3 is the dimensionality of vertex position
			// The prefix "oc_" means object coordinates
			rp.shader = new ShaderProgram(new Shader(GL_VERTEX_SHADER, VSHADER_FN), new Shader(GL_FRAGMENT_SHADER, FSHADER_FN), "colour");
			rp.shader.bindDataToShader("oc_position", rp.mesh.vertex_handle, 3);
			// Tell vertex shader where it can find vertex normals. 3 is the dimensionality of vertex normals
			rp.shader.bindDataToShader("oc_normal", rp.mesh.normal_handle, 3);
			// Tell vertex shader where it can find texture coordinates. 2 is the dimensionality of texture coordinates
			rp.shader.bindDataToShader("texcoord", rp.mesh.tex_handle, 2);
			
			rp.texture = new Texture();
			if (rp == head) rp.texture.load("resources/cubemap_head.png");
			else rp.texture.load("resources/cubemap.png");
			rp.transform = new Matrix4f();
			rp.executeInitialTransformation();
		}
		
		body.subParts = new ArrayList<>(Arrays.asList(rightArm, leftArm, rightLeg, leftLeg, head));
		
		body.texture.load("resources/cubemap.png");
		
		//initial transformation of the robot
		
		T_body = new Matrix4f();
		
		
		transfer(body, T_body);
		
		// M_body = E_body; // E_body, ovo program zove na kraju

		
		// TODO: Create right arm node
		// right_arm_mesh = new CubeMesh();
		
		// right_arm_shader = new ShaderProgram(new Shader(GL_VERTEX_SHADER, VSHADER_FN), new Shader(GL_FRAGMENT_SHADER, FSHADER_FN), "colour");
		// right_arm_shader.bindDataToShader("oc_position", right_arm_mesh.vertex_handle, 3);
		// right_arm_shader.bindDataToShader("oc_normal", right_arm_mesh.normal_handle, 3);
		// right_arm_shader.bindDataToShader("texcoord", right_arm_mesh.tex_handle,  2);
		
		// TODO: Initialise Texturing
		// right_arm_texture = new Texture();
		
		// TODO: Build Right Arm's Transformation Matrix (rotate the right arm around its end)
		// M_right_arm = new Matrix4f();
		// E_right_arm = new Matrix4f();
		
		// initial position of right arm
		/*E_right_arm.translate(0.0f, 1.8f, 1.5f)
		   		   .translate(0.0f, -2.0f * (float)Math.cos(Math.toRadians(-15.0f)), -2.0f * (float)Math.sin(Math.toRadians(-15.0f)))
		   		   .rotateX((float)Math.toRadians(-15.0f))
		   		   .scale(0.5f, 2.0f, 0.5f);*/
		
		// M_right_arm = E_right_arm;
		
		/*M_right_arm.translate(0.0f, 1.8f, 1.0f)
				   .translate(0.0f, -(float)Math.cos(Math.toRadians(-15.0f)), -(float)Math.sin(Math.toRadians(-15.0f)))
				   .rotateX((float)Math.toRadians(-15.0f))
				   .scale(0.5f, 2.0f, 0.5f);*/
		
		
		/*right_arm_transform.translate(0.0f, -2.2f, 2.5f)
						   .rotateX((float)Math.toRadians(-15.0f))
						   .scale(0.4f, 2.2f, 0.4f)
						   .translate(0.0f, 1.0f, 0.0f);*/
		
		
		// TODO: Complete robot

	}
	
	
	private void transfer(RobotPart rp, Matrix4f move) {
		rp.transform.mulLocal(move); // rp.transform je model matrica za rp
		if (rp.subParts != null) {
			for (RobotPart subPart: rp.subParts) {
				transfer(subPart, move);
			}
		}
	}

	/**
	 * Updates the scene and then renders the CubeRobot
	 * @param camera - Camera to be used for rendering
	 * @param deltaTime		- Time taken to render this frame in seconds (= 0 when the application is paused)
	 * @param elapsedTime	- Time elapsed since the beginning of this program in milliseconds
	 */
	public void render(Camera camera, float deltaTime, long elapsedTime) {
		
		// TODO: Animate Body. Translate the body as a function of time
		
		T_body = new Matrix4f().rotateY((float)Math.toRadians(deltaTime * 25));
		//T_body = new Matrix4f();
		//T_body.translate((float) (3*deltaTime), 0, 0); // "move"
		
		
		
		
		//T_body.mul(M_body, M_body); // result of multiplication is stored in second argument

		
		
		
		
		// TODO: Animate Arm. Rotate the right arm around its end as a function of time
		
		
		
		
		
		/*T_right_arm.translate(0.0f, 1.8f, 1.5f)
				   .rotateZ((float)Math.toRadians(deltaTime * 100))
				   .translate(0.0f, -1.8f, -1.5f);
				   
				   
		*/
		
		
		
		
		/*T_right_arm.translate(0.0f, -0.2f, 2.0f)
				   .translate(0.0f, (float)Math.cos(Math.toRadians(-15.0f)), (float)Math.sin(Math.toRadians(-15.0f)))
				   .rotateZ((float)Math.toRadians(deltaTime * 100))
				   .translate(0.0f, -(float)Math.cos(Math.toRadians(-15.0f)), -(float)Math.sin(Math.toRadians(-15.0f)))
				   .translate(0.0f, 0.2f, -2.0f);*/
		// T_right_arm.mul(M_right_arm, M_right_arm);
		
		
		
		
		// T_body.mul(M_right_arm, M_right_arm);
		
		
		
						   
		
		//renderMesh(camera, body_mesh, M_body, body_shader, body_texture);
		
		// TODO: Chain transformation matrices of the arm and body (Scene Graph)
		transfer(body, T_body);
		// transfer(rightArm, T_right_arm);
		// TODO: Render Arm.
		//renderMesh(camera, right_arm_mesh, M_right_arm, right_arm_shader, right_arm_texture);

		
		//TODO: Render rest of the robot
		for (RobotPart rp: parts) {
			renderMesh(camera, rp.mesh, rp.transform, rp.shader, rp.texture);
		}
	}
	
	/**
	 * Draw mesh from a camera perspective
	 * @param camera		- Camera to be used for rendering
	 * @param mesh			- mesh to render
	 * @param modelMatrix	- model transformation matrix of this mesh
	 * @param shader		- shader to colour this mesh
	 * @param texture		- texture image to be used by the shader
	 */
	public void renderMesh(Camera camera, Mesh mesh , Matrix4f modelMatrix, ShaderProgram shader, Texture texture) {
		// If shaders modified on disk, reload them
		shader.reloadIfNeeded(); 
		shader.useProgram();

		// Step 2: Pass relevant data to the vertex shader
		
		// compute and upload MVP
		Matrix4f mvp_matrix = new Matrix4f(camera.getProjectionMatrix()).mul(camera.getViewMatrix()).mul(modelMatrix);
		shader.uploadMatrix4f(mvp_matrix, "mvp_matrix");
		
		// Upload Model Matrix and Camera Location to the shader for Phong Illumination
		shader.uploadMatrix4f(modelMatrix, "m_matrix");
		shader.uploadVector3f(camera.getCameraPosition(), "wc_camera_position");
		
		// Transformation by a nonorthogonal matrix does not preserve angles
		// Thus we need a separate transformation matrix for normals
		Matrix3f normal_matrix = new Matrix3f();
		//TODO: Calculate normal transformation matrix
		modelMatrix.get3x3(normal_matrix);
		normal_matrix = normal_matrix.invert().transpose(); // G = (M^-1)^T
		
		
		shader.uploadMatrix3f(normal_matrix, "normal_matrix");
		
		// Step 3: Draw our VertexArray as triangles
		// Bind Textures
		texture.bindTexture();
		shader.bindTextureToShader("tex", 0);
		skybox.bindCubemap();
		shader.bindTextureToShader("skybox", 1);
		
		// draw
		
		// Bind the existing VertexArray
		glBindVertexArray(mesh.vertexArrayObj);
		
		// Draws the geometry associated with the currently bound VertexArray
		// Draw it as triangles
		glDrawElements(GL_TRIANGLES, mesh.no_of_triangles, GL_UNSIGNED_INT, 0); 
		glBindVertexArray(0);             // Remove the binding, i.e. unbind the VertexArray object
		
        // Unbind texture
		texture.unBindTexture();
		skybox.unBindCubemap();
	}
}
