package com.example.goblinminers.client.model;

import com.example.goblinminers.GoblinMinersMod;
import com.example.goblinminers.entity.MinerGoblinEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class MinerGoblinModel extends EntityModel<MinerGoblinEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(GoblinMinersMod.MOD_ID, "miner_goblin"), "main");

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public MinerGoblinModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.rightArm = root.getChild("right_arm");
        this.leftArm = root.getChild("left_arm");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        // Baby-zombie proportioned goblin (compact, big head, 3D ears, 3D backpack, 3D drill)
        PartDefinition head = partdefinition.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                        .texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.35F))
                        // Left & Right pointy goblin ears
                        .texOffs(32, 11).addBox(-7.0F, -5.0F, -1.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(52, 11).addBox(4.0F, -5.0F, -1.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 12.0F, 0.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 6.0F, 4.0F, new CubeDeformation(0.25F))
                        // 3D Miner Backpack on the back
                        .texOffs(32, 36).addBox(-3.0F, 0.5F, 2.0F, 6.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 12.0F, 0.0F));

        // Right arm with integrated 3D conical drill attached to hand (no separate item needed)
        PartDefinition rightArm = partdefinition.addOrReplaceChild("right_arm",
                CubeListBuilder.create()
                        .texOffs(40, 16).addBox(-3.0F, -1.0F, -2.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                        // Drill housing
                        .texOffs(40, 40).addBox(-3.5F, 4.0F, -4.5F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                        // Drill middle cone
                        .texOffs(42, 41).addBox(-3.0F, 4.5F, -7.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                        // Drill tip
                        .texOffs(44, 42).addBox(-2.5F, 5.0F, -10.0F, 2.0F, 2.0F, 2.5F, new CubeDeformation(0.0F)),
                PartPose.offset(-4.0F, 13.0F, 0.5F));

        PartDefinition leftArm = partdefinition.addOrReplaceChild("left_arm",
                CubeListBuilder.create()
                        .texOffs(32, 48).addBox(0.0F, -1.0F, -2.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(4.0F, 13.0F, 0.5F));

        PartDefinition rightLeg = partdefinition.addOrReplaceChild("right_leg",
                CubeListBuilder.create()
                        .texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-2.0F, 18.0F, 0.0F));

        PartDefinition leftLeg = partdefinition.addOrReplaceChild("left_leg",
                CubeListBuilder.create()
                        .texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(2.0F, 18.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(MinerGoblinEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.head.xRot = headPitch * ((float) Math.PI / 180F);

        // Subtle idle drilling motion on right arm
        this.rightArm.xRot = -0.35F + Mth.sin(ageInTicks * 0.45F) * 0.08F;
        this.leftArm.xRot = Mth.cos(limbSwing * 0.6662F) * 0.8F * limbSwingAmount;
        this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount;
        this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.0F * limbSwingAmount;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
