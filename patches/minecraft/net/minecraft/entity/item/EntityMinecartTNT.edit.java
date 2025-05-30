
# Eagler Context Redacted Diff
# Copyright (c) 2025 lax1dude. All rights reserved.

# Version: 1.0
# Author: lax1dude

> INSERT  2 : 3  @  2

+ import net.lax1dude.eaglercraft.v1_8.opengl.ext.deferred.DynamicLightManager;

> DELETE  3  @  3 : 4

> INSERT  159 : 178  @  159

+ 
+ 	protected void renderDynamicLightsEaglerAt(double entityX, double entityY, double entityZ, double renderX,
+ 			double renderY, double renderZ, float partialTicks, boolean isInFrustum) {
+ 		super.renderDynamicLightsEaglerAt(entityX, entityY, entityZ, renderX, renderY, renderZ, partialTicks,
+ 				isInFrustum);
+ 		if (minecartTNTFuse > -1 && minecartTNTFuse / 5 % 2 == 0) {
+ 			float dynamicLightMag = 10.0f;
+ 			DynamicLightManager.renderDynamicLight("entity_" + getEntityId() + "_tnt_flash", entityX, entityY + 0.5,
+ 					entityZ, dynamicLightMag, dynamicLightMag * 0.7792f, dynamicLightMag * 0.618f, false);
+ 		}
+ 	}
+ 
+ 	protected float getEaglerDynamicLightsValueSimple(float partialTicks) {
+ 		float f = super.getEaglerDynamicLightsValueSimple(partialTicks);
+ 		if (minecartTNTFuse > -1 && minecartTNTFuse / 5 % 2 == 0) {
+ 			f = Math.min(f + 0.75f, 1.25f);
+ 		}
+ 		return f;
+ 	}

> EOF
