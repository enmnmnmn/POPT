package top.o_illusions.popt.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.*;
import org.joml.Matrix4f;


public class PoptClient implements ClientModInitializer {



    @Override
    public void onInitializeClient() {
        WorldRenderEvents.END.register(context -> {
            MatrixStack matrixStack = context.matrixStack();
            Camera camera = context.camera();
            render(matrixStack, camera);
        });
    }


    public void render(MatrixStack matrixStack, Camera camera) {
        Tessellator tessellator = Tessellator.getInstance();
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player == null) return;

//        BowItem
//        FishingRodItem


        Vec3d quadsPos = new Vec3d(0, -59, 0);
        Vec3d cameraPos = camera.getPos();
        Matrix4f rotation = new Matrix4f().rotation(camera.getRotation()).invert();
        quadsPos = quadsPos.subtract(cameraPos);

        BufferBuilder quadsBuilder = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        BufferBuilder lineBuilder = tessellator.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);


        RenderSystem.enableBlend();

        matrixStack.push();
        matrixStack.multiplyPositionMatrix(rotation);
        matrixStack.translate(quadsPos.x, quadsPos.y, quadsPos.z);
        matrixStack.scale(16*4, 16*4, 16*4);

        Matrix4f quadModelMatrix = matrixStack.peek().getPositionMatrix();
        renderBox(quadsBuilder, quadModelMatrix, 0x8000FFFF);

        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        BufferRenderer.drawWithGlobalProgram(quadsBuilder.end());

        matrixStack.pop();


        matrixStack.push();
        matrixStack.multiplyPositionMatrix(rotation);
        matrixStack.translate(quadsPos.x, quadsPos.y, quadsPos.z);
        matrixStack.scale(16*4, 16*4, 16*4);

        Matrix4f lineModelMatrix = matrixStack.peek().getPositionMatrix();
        renderLineBox(lineBuilder, lineModelMatrix, 0xFFFFFFFF);

        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        BufferRenderer.drawWithGlobalProgram(lineBuilder.end());

        matrixStack.pop();

        RenderSystem.disableBlend();
    }

    private void renderBox(BufferBuilder builder, Matrix4f modelMatrix, int argb) {
        builder.vertex(modelMatrix, 0, 1, 0).color(argb);
        builder.vertex(modelMatrix, 0, 1, 1).color(argb);
        builder.vertex(modelMatrix, 1, 1, 1).color(argb);
        builder.vertex(modelMatrix, 1, 1, 0).color(argb);

        builder.vertex(modelMatrix, 0, 0, 0).color(argb);
        builder.vertex(modelMatrix, 1, 0, 0).color(argb);
        builder.vertex(modelMatrix, 1, 0, 1).color(argb);
        builder.vertex(modelMatrix, 0, 0, 1).color(argb);

        builder.vertex(modelMatrix, 1, 0, 0).color(argb);
        builder.vertex(modelMatrix, 1, 1, 0).color(argb);
        builder.vertex(modelMatrix, 1, 1, 1).color(argb);
        builder.vertex(modelMatrix, 1, 0, 1).color(argb);

        builder.vertex(modelMatrix, 0, 0, 0).color(argb);
        builder.vertex(modelMatrix, 0, 0, 1).color(argb);
        builder.vertex(modelMatrix, 0, 1, 1).color(argb);
        builder.vertex(modelMatrix, 0, 1, 0).color(argb);

        builder.vertex(modelMatrix, 0, 0, 1).color(argb);
        builder.vertex(modelMatrix, 1, 0, 1).color(argb);
        builder.vertex(modelMatrix, 1, 1, 1).color(argb);
        builder.vertex(modelMatrix, 0, 1, 1).color(argb);

        builder.vertex(modelMatrix, 0, 0, 0).color(argb);
        builder.vertex(modelMatrix, 0, 1, 0).color(argb);
        builder.vertex(modelMatrix, 1, 1, 0).color(argb);
        builder.vertex(modelMatrix, 1, 0, 0).color(argb);

    }

    private void renderLineBox(BufferBuilder builder, Matrix4f modelMatrix, int argb) {
        builder.vertex(modelMatrix, 0, 0, 0).color(argb);
        builder.vertex(modelMatrix, 0, 1, 0).color(argb);

        builder.vertex(modelMatrix, 0, 0, 0).color(argb);
        builder.vertex(modelMatrix, 0, 0, 1).color(argb);

        builder.vertex(modelMatrix, 0, 0, 0).color(argb);
        builder.vertex(modelMatrix, 1, 0, 0).color(argb);
        

        builder.vertex(modelMatrix, 1, 0, 1).color(argb);
        builder.vertex(modelMatrix, 1, 1, 1).color(argb);

        builder.vertex(modelMatrix, 1, 0, 1).color(argb);
        builder.vertex(modelMatrix, 1, 0, 0).color(argb);

        builder.vertex(modelMatrix, 1, 0, 1).color(argb);
        builder.vertex(modelMatrix, 0, 0, 1).color(argb);


        builder.vertex(modelMatrix, 1, 1, 0).color(argb);
        builder.vertex(modelMatrix, 1, 0, 0).color(argb);

        builder.vertex(modelMatrix, 1, 1, 0).color(argb);
        builder.vertex(modelMatrix, 0, 1, 0).color(argb);

        builder.vertex(modelMatrix, 1, 1, 0).color(argb);
        builder.vertex(modelMatrix, 1, 1, 1).color(argb);


        builder.vertex(modelMatrix, 0, 1, 1).color(argb);
        builder.vertex(modelMatrix, 0, 0, 1).color(argb);

        builder.vertex(modelMatrix, 0, 1, 1).color(argb);
        builder.vertex(modelMatrix, 1, 1, 1).color(argb);

        builder.vertex(modelMatrix, 0, 1, 1).color(argb);
        builder.vertex(modelMatrix, 0, 1, 0).color(argb);

    }
}
