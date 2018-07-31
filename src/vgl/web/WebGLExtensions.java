package vgl.web;

import com.shc.webgl4j.client.ANGLE_instanced_arrays;
import com.shc.webgl4j.client.EXT_blend_minmax;
import com.shc.webgl4j.client.EXT_frag_depth;
import com.shc.webgl4j.client.EXT_texture_filter_anisotropic;
import com.shc.webgl4j.client.OES_element_index_uint;
import com.shc.webgl4j.client.OES_standard_derivatives;
import com.shc.webgl4j.client.OES_texture_float;
import com.shc.webgl4j.client.OES_texture_float_linear;
import com.shc.webgl4j.client.OES_texture_half_float;
import com.shc.webgl4j.client.OES_texture_half_float_linear;
import com.shc.webgl4j.client.OES_vertex_array_object;
import com.shc.webgl4j.client.WEBGL_compressed_texture_s3tc;
import com.shc.webgl4j.client.WEBGL_debug_renderer_info;
import com.shc.webgl4j.client.WEBGL_debug_shaders;
import com.shc.webgl4j.client.WEBGL_depth_texture;
import com.shc.webgl4j.client.WEBGL_draw_buffers;
import com.shc.webgl4j.client.WEBGL_lose_context;

public class WebGLExtensions {

	public static void tryEnableAll() {
		if (ANGLE_instanced_arrays.isSupported() && !ANGLE_instanced_arrays.isExtensionEnabled()) {
			ANGLE_instanced_arrays.enableExtension();
		}
		if (EXT_blend_minmax.isSupported() && !EXT_blend_minmax.isExtensionEnabled()) {
			EXT_blend_minmax.enableExtension();
		}
		if (EXT_frag_depth.isSupported() && !EXT_frag_depth.isExtensionEnabled()) {
			EXT_frag_depth.enableExtension();
		}
		if (EXT_texture_filter_anisotropic.isSupported() && !EXT_texture_filter_anisotropic.isExtensionEnabled()) {
			EXT_texture_filter_anisotropic.enableExtension();
		}
		if (OES_element_index_uint.isSupported() && !OES_element_index_uint.isExtensionEnabled()) {
			OES_element_index_uint.enableExtension();
		}
		if (OES_standard_derivatives.isSupported() && !OES_standard_derivatives.isExtensionEnabled()) {
			OES_standard_derivatives.enableExtension();
		}
		if (OES_texture_float_linear.isSupported() && !OES_texture_float_linear.isExtensionEnabled()) {
			OES_texture_float_linear.enableExtension();
		}
		if (OES_texture_float.isSupported() && !OES_texture_float.isExtensionEnabled()) {
			OES_texture_float.enableExtension();
		}
		if (OES_texture_half_float.isSupported() && !OES_texture_half_float.isExtensionEnabled()) {
			OES_texture_half_float.enableExtension();
		}
		if (OES_texture_half_float_linear.isSupported() && !OES_texture_half_float_linear.isExtensionEnabled()) {
			OES_texture_half_float_linear.enableExtension();
		}
		if (OES_vertex_array_object.isSupported() && !OES_vertex_array_object.isExtensionEnabled()) {
			OES_vertex_array_object.enableExtension();
		}
		if (WEBGL_compressed_texture_s3tc.isSupported() && !WEBGL_compressed_texture_s3tc.isExtensionEnabled()) {
			WEBGL_compressed_texture_s3tc.enableExtension();
		}
		if (WEBGL_debug_renderer_info.isSupported() && !WEBGL_debug_renderer_info.isExtensionEnabled()) {
			WEBGL_debug_renderer_info.enableExtension();
		}
		if (WEBGL_debug_shaders.isSupported() && !WEBGL_debug_shaders.isExtensionEnabled()) {
			WEBGL_debug_shaders.enableExtension();
		}
		if (WEBGL_depth_texture.isSupported() && !WEBGL_depth_texture.isExtensionEnabled()) {
			WEBGL_depth_texture.enableExtension();
		}
		if (WEBGL_draw_buffers.isSupported() && !WEBGL_draw_buffers.isExtensionEnabled()) {
			WEBGL_draw_buffers.enableExtension();
		}
		if (WEBGL_lose_context.isSupported() && !WEBGL_lose_context.isExtensionEnabled()) {
			WEBGL_lose_context.enableExtension();
		}
	}

}
