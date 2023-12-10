package game;


import imgui.ImGui;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL45;

import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;

public class Main extends Application {
    public static void main(String[] args) {
        launch( new Main());
    }

    @Override
    protected void configure(Configuration config) {
        config.setTitle("Dear ImGui is Awesome!");
    }

    @Override
    public void process() {
        ImGui.text("FPS: " + getFPS());


    }
}