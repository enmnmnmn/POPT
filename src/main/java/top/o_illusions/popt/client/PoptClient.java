package top.o_illusions.popt.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.*;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

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
        if (player == null) {
            return;
        }


        Vec3d quadsPos = new Vec3d(0, -59, 0);
        Vec3d cameraPos = camera.getPos();
        Matrix4f rotation = new Matrix4f().rotation(camera.getRotation()).invert();
        quadsPos = quadsPos.subtract(cameraPos);

        BufferBuilder builder = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);


        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);

        matrixStack.push();
        matrixStack.multiplyPositionMatrix(rotation);
        matrixStack.translate(quadsPos.x, quadsPos.y, quadsPos.z);



        Matrix4f modelMatrix = matrixStack.peek().getPositionMatrix();

        builder.vertex(modelMatrix, 0, 1, 0).color(0x80FFFFFF);
        builder.vertex(modelMatrix, 0, 1, 1).color(0x80FFFFFF);
        builder.vertex(modelMatrix, 1, 1, 1).color(0x80FFFFFF);
        builder.vertex(modelMatrix, 1, 1, 0).color(0x80FFFFFF);

        builder.vertex(modelMatrix, 0, 0, 0).color(0x80FFFFFF);
        builder.vertex(modelMatrix, 1, 0, 0).color(0x80FFFFFF);
        builder.vertex(modelMatrix, 1, 0, 1).color(0x80FFFFFF);
        builder.vertex(modelMatrix, 0, 0, 1).color(0x80FFFFFF);

        builder.vertex(modelMatrix, 1, 0, 0).color(0x80FFFFFF);
        builder.vertex(modelMatrix, 1, 1, 0).color(0x80FFFFFF);
        builder.vertex(modelMatrix, 1, 1, 1).color(0x80FFFFFF);
        builder.vertex(modelMatrix, 1, 0, 1).color(0x80FFFFFF);

        builder.vertex(modelMatrix, 0, 0, 0).color(0x80FFFFFF);
        builder.vertex(modelMatrix, 0, 0, 1).color(0x80FFFFFF);
        builder.vertex(modelMatrix, 0, 1, 1).color(0x80FFFFFF);
        builder.vertex(modelMatrix, 0, 1, 0).color(0x80FFFFFF);

        builder.vertex(modelMatrix, 0, 0, 1).color(0x80FFFFFF);
        builder.vertex(modelMatrix, 1, 0, 1).color(0x80FFFFFF);
        builder.vertex(modelMatrix, 1, 1, 1).color(0x80FFFFFF);
        builder.vertex(modelMatrix, 0, 1, 1).color(0x80FFFFFF);

        builder.vertex(modelMatrix, 0, 0, 0).color(0x80FFFFFF);
        builder.vertex(modelMatrix, 0, 1, 0).color(0x80FFFFFF);
        builder.vertex(modelMatrix, 1, 1, 0).color(0x80FFFFFF);
        builder.vertex(modelMatrix, 1, 0, 0).color(0x80FFFFFF);


        BufferRenderer.drawWithGlobalProgram(builder.end());

        matrixStack.pop();

        RenderSystem.disableBlend();
    }
}
