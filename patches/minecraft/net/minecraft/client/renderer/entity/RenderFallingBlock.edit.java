
# Eagler Context Redacted Diff
# Copyright (c) 2025 lax1dude. All rights reserved.

# Version: 1.0
# Author: lax1dude

> INSERT  2 : 6  @  2

+ import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
+ import net.lax1dude.eaglercraft.v1_8.opengl.VertexFormat;
+ import net.lax1dude.eaglercraft.v1_8.opengl.WorldRenderer;
+ import net.lax1dude.eaglercraft.v1_8.opengl.ext.deferred.DeferredStateManager;

> DELETE  4  @  4 : 5

> DELETE  1  @  1 : 4

> CHANGE  28 : 31  @  28 : 29

~ 					worldrenderer.begin(7, (DeferredStateManager.isDeferredRenderer()
~ 					/* || DynamicLightsStateManager.isDynamicLightsRender() */) ? VertexFormat.BLOCK_SHADERS
~ 							: DefaultVertexFormats.BLOCK);

> EOF
