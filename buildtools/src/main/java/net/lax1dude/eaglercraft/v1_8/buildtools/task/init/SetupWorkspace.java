package net.lax1dude.eaglercraft.v1_8.buildtools.task.init;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import net.lax1dude.eaglercraft.v1_8.buildtools.EaglerBuildToolsConfig;
import net.lax1dude.eaglercraft.v1_8.buildtools.task.diff.ApplyPatchesToZip;

/**
 * Copyright (c) 2022-2023 lax1dude. All Rights Reserved.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 */
public class SetupWorkspace {
	
	public static boolean setupWorkspace() {
		return setupWorkspace0(false);
	}
	
	public static boolean pullRequestTest() {
		return setupWorkspace0(true);
	}
	
	private static boolean setupWorkspace0(boolean applyPullRequest) {
		File tmp = EaglerBuildToolsConfig.getTemporaryDirectory();
		File dst = EaglerBuildToolsConfig.getWorkspaceDirectory();
		try {
			return setupWorkspace1(tmp, dst, applyPullRequest);
		}catch(Throwable t) {
			System.err.println();
			if(applyPullRequest) {
				System.err.println("Exception encountered while running task 'pullrequest_test'!");
			}else {
				System.err.println("Exception encountered while running task 'workspace'!");
			}
			t.printStackTrace();
			return false;
		}
	}
	
	private static boolean setupWorkspace1(File btTmpDirectory, File workspaceDirectory, boolean applyPullRequest) throws Throwable {
		
		boolean wsExist = workspaceDirectory.exists();
		if(wsExist && !(workspaceDirectory.isDirectory() && workspaceDirectory.list().length == 0)) {
			System.err.println();
			System.err.println("WARNING: A workspace already exists in \"" + workspaceDirectory.getAbsolutePath() + "\"!");
			System.err.println();
			System.err.println("Any changes you've made to the code will be lost!");
			System.err.println();
			System.out.print("Do you want to reset the workspace? [Y/n]: ");
			
			String ret = "n";
			try {
				ret = (new BufferedReader(new InputStreamReader(System.in))).readLine();
			}catch(IOException ex) {
				// ?
			}
			ret = ret.toLowerCase();
			if(!ret.startsWith("y")) {
				System.out.println();
				System.out.println("Ok nice, the workspace folder will not be reset. (thank god)");
				System.out.println();
				System.out.println("Edit 'buildtools_config.json' to set up a different workspace folder");
				return true;
			}else {
				try {
					FileUtils.deleteDirectory(workspaceDirectory);
					wsExist = false;
				}catch(IOException ex) {
					System.err.println("ERROR: Could not delete \"" + workspaceDirectory.getAbsolutePath() + "\"!");
					ex.printStackTrace();
					return false;
				}
			}
		}
		
		File mcTmpDirectory = new File(btTmpDirectory, "MinecraftSrc");
		File minecraftResJar = new File(mcTmpDirectory, "minecraft_res_patch.jar");
		File minecraftJavadocTmp = new File(mcTmpDirectory, "minecraft_src_javadoc.jar");
		
		System.out.println();
		System.out.println("Setting up dev workspace in \"" + workspaceDirectory.getAbsolutePath() + "\"...");
		System.out.println();
		
		if(!workspaceDirectory.isDirectory() && !workspaceDirectory.mkdirs()) {
			System.err.println("ERROR: could not create \"" + workspaceDirectory.getAbsolutePath() + "\"!");
			throw new IOException("Could not create \"" + workspaceDirectory.getAbsolutePath() + "\"!");
		}
		
		if(!minecraftJavadocTmp.isFile()) {
			System.err.println("ERROR: could not find 'minecraft_src_javadoc.jar' in your current temporary directory!");
			System.err.println("Run the 'init' command again to generate it");
			return false;
		}
		
		if(!minecraftResJar.isFile()) {
			System.err.println("ERROR: could not find 'minecraft_res_patch.jar' in your current temporary directory!");
			System.err.println("Run the 'init' command again to generate it");
			return false;
		}

		File repoSources = new File("./sources");
		File repoSourcesSetup = new File(repoSources, "setup/workspace_template");
		File repoSourcesMain = new File(repoSources, "main/java");
		File repoSourcesTeaVM = new File(repoSources, "teavm/java");
		File repoSourcesTeaVMRes = new File(repoSources, "teavm/resources");
		File repoSourcesLWJGL = new File(repoSources, "lwjgl/java");
		File repoSourcesProtoGame = new File(repoSources, "protocol-game/java");
		File repoSourcesProtoRelay = new File(repoSources, "protocol-relay/java");
		File repoSourcesPlatformAPI = new File(repoSources, "platform-api/java");
		File repoSourcesBootMenu = new File(repoSources, "teavm-boot-menu/java");
		File repoSourcesTeavmCRes = new File(repoSources, "teavmc-classpath/resources");
		File repoSourcesWASMGCTeaVMJava = new File(repoSources, "wasm-gc-teavm/java");
		File repoSourcesWASMGCTeaVMJS = new File(repoSources, "wasm-gc-teavm/js");
		File repoSourcesWASMGCTeaVMBootstrapJS = new File(repoSources, "wasm-gc-teavm-bootstrap/js");
		File repoSourcesWASMGCTeaVMLoaderC = new File(repoSources, "wasm-gc-teavm-loader/c");
		File repoSourcesWASMGCTeaVMLoaderJS = new File(repoSources, "wasm-gc-teavm-loader/js");
		File repoSourcesResources = new File(repoSources, "resources");
		File srcMainJava = new File(workspaceDirectory, "src/main/java");
		File srcGameJava = new File(workspaceDirectory, "src/game/java");
		File srcLWJGLJava = new File(workspaceDirectory, "src/lwjgl/java");
		File srcTeaVMJava = new File(workspaceDirectory, "src/teavm/java");
		File srcTeaVMRes = new File(workspaceDirectory, "src/teavm/resources");
		File srcProtoGame = new File(workspaceDirectory, "src/protocol-game/java");
		File srcProtoRelay = new File(workspaceDirectory, "src/protocol-relay/java");
		File srcPlatformAPI = new File(workspaceDirectory, "src/platform-api/java");
		File srcBootMenu = new File(workspaceDirectory, "src/teavm-boot-menu/java");
		File srcTeavmCRes = new File(workspaceDirectory, "src/teavmc-classpath/resources");
		File srcWASMGCTeaVMJava = new File(workspaceDirectory, "src/wasm-gc-teavm/java");
		File srcWASMGCTeaVMJS = new File(workspaceDirectory, "src/wasm-gc-teavm/js");
		File srcWASMGCTeaVMBootstrapJS = new File(workspaceDirectory, "src/wasm-gc-teavm-bootstrap/js");
		File srcWASMGCTeaVMLoaderC = new File(workspaceDirectory, "src/wasm-gc-teavm-loader/c");
		File srcWASMGCTeaVMLoaderJS = new File(workspaceDirectory, "src/wasm-gc-teavm-loader/js");
		File resourcesExtractTo = new File(workspaceDirectory, "desktopRuntime/resources");
		File mcLanguagesZip = new File(mcTmpDirectory, "minecraft_languages.zip");
		File localLibsZip = new File(repoSources, "setup/local-libs.zip");
		File mcLanguagesExtractTo = new File(workspaceDirectory, "target_teavm_javascript/javascript/lang");
		File localLibsExtractTo = new File(workspaceDirectory, "gradle/local-libs");

		System.out.println("Copying files from \"/setup/workspace_template/\" to \"" + workspaceDirectory.getName() + "\"...");
		
		try {
			FileUtils.copyDirectory(repoSourcesSetup, workspaceDirectory);
		}catch(IOException ex) {
			System.err.println("ERROR: could not copy \"/setup/workspace_template/\" to \"" + workspaceDirectory.getAbsolutePath() + "\"!");
			throw ex;
		}
		
		File existingGi = new File(workspaceDirectory, ".gitignore");
		if((existingGi.exists() && !existingGi.delete()) || !(new File(workspaceDirectory, ".gitignore.default").renameTo(existingGi))) {
			System.err.println("ERROR: Could not rename \".gitignore.default\" to \".gitignore\" in the workspace directory!");
		}
		
		existingGi = new File(workspaceDirectory, "target_teavm_javascript/.gitignore");
		if((existingGi.exists() && !existingGi.delete()) || !(new File(workspaceDirectory, "target_teavm_javascript/.gitignore.default").renameTo(existingGi))) {
			System.err.println("ERROR: Could not rename \"target_teavm_javascript/.gitignore.default\" to \"target_teavm_javascript/.gitignore\" in the workspace directory!");
		}
		
		existingGi = new File(workspaceDirectory, "target_teavm_wasm_gc/.gitignore");
		if((existingGi.exists() && !existingGi.delete()) || !(new File(workspaceDirectory, "target_teavm_wasm_gc/.gitignore.default").renameTo(existingGi))) {
			System.err.println("ERROR: Could not rename \"target_teavm_wasm_gc/.gitignore.default\" to \"target_teavm_wasm_gc/.gitignore\" in the workspace directory!");
		}
		
		if(repoSourcesTeaVM.isDirectory()) {
			System.out.println("Copying files from \"/sources/teavm/java/\" to workspace...");
			
			try {
				if(!srcTeaVMJava.isDirectory() && !srcTeaVMJava.mkdirs()) {
					System.err.println("ERROR: Could not create destination directory!");
					return false;
				}
				FileUtils.copyDirectory(repoSourcesTeaVM, srcTeaVMJava);
			}catch(IOException ex) {
				System.err.println("ERROR: could not copy \"/sources/teavm/java/\" to \"" + srcTeaVMJava.getAbsolutePath() + "\"!");
				throw ex;
			}
		}
		
		if(repoSourcesTeaVMRes.isDirectory()) {
			System.out.println("Copying files from \"/sources/teavm/resources/\" to workspace...");
			
			try {
				if(!srcTeaVMRes.isDirectory() && !srcTeaVMRes.mkdirs()) {
					System.err.println("ERROR: Could not create destination directory!");
					return false;
				}
				FileUtils.copyDirectory(repoSourcesTeaVMRes, srcTeaVMRes);
			}catch(IOException ex) {
				System.err.println("ERROR: could not copy \"/sources/teavm/resources/\" to \"" + srcTeaVMRes.getAbsolutePath() + "\"!");
				throw ex;
			}
		}
		
		System.out.println("Copying files from \"/sources/main/java/\" to workspace...");
		
		try {
			if(!srcMainJava.isDirectory() && !srcMainJava.mkdirs()) {
				System.err.println("ERROR: Could not create destination directory!");
				return false;
			}
			FileUtils.copyDirectory(repoSourcesMain, srcMainJava);
		}catch(IOException ex) {
			System.err.println("ERROR: could not copy \"/sources/main/java/\" to \"" + srcMainJava.getAbsolutePath() + "\"!");
			throw ex;
		}
		
		if(repoSourcesProtoGame.isDirectory()) {
			System.out.println("Copying files from \"/sources/protocol-game/java/\" to workspace...");
			
			try {
				if(!srcProtoGame.isDirectory() && !srcProtoGame.mkdirs()) {
					System.err.println("ERROR: Could not create destination directory!");
					return false;
				}
				FileUtils.copyDirectory(repoSourcesProtoGame, srcProtoGame);
			}catch(IOException ex) {
				System.err.println("ERROR: could not copy \"/sources/protocol-game/java/\" to \"" + srcProtoGame.getAbsolutePath() + "\"!");
				throw ex;
			}
		}

		if(repoSourcesProtoRelay.isDirectory()) {
			System.out.println("Copying files from \"/sources/protocol-relay/java/\" to workspace...");
	
			try {
				if(!srcProtoRelay.isDirectory() && !srcProtoRelay.mkdirs()) {
					System.err.println("ERROR: Could not create destination directory!");
					return false;
				}
				FileUtils.copyDirectory(repoSourcesProtoRelay, srcProtoRelay);
			}catch(IOException ex) {
				System.err.println("ERROR: could not copy \"/sources/protocol-relay/java/\" to \"" + srcProtoRelay.getAbsolutePath() + "\"!");
				throw ex;
			}
		}

		if(repoSourcesPlatformAPI.isDirectory()) {
			System.out.println("Copying files from \"/sources/platform-api/java/\" to workspace...");
	
			try {
				if(!srcPlatformAPI.isDirectory() && !srcPlatformAPI.mkdirs()) {
					System.err.println("ERROR: Could not create destination directory!");
					return false;
				}
				FileUtils.copyDirectory(repoSourcesPlatformAPI, srcPlatformAPI);
			}catch(IOException ex) {
				System.err.println("ERROR: could not copy \"/sources/platform-api/java/\" to \"" + srcPlatformAPI.getAbsolutePath() + "\"!");
				throw ex;
			}
		}

		if(repoSourcesTeavmCRes.isDirectory()) {
			System.out.println("Copying files from \"/sources/teavmc-classpath/resources/\" to workspace...");
			
			try {
				if(!srcTeavmCRes.isDirectory() && !srcTeavmCRes.mkdirs()) {
					System.err.println("ERROR: Could not create destination directory!");
					return false;
				}
				FileUtils.copyDirectory(repoSourcesTeavmCRes, srcTeavmCRes);
			}catch(IOException ex) {
				System.err.println("ERROR: could not copy \"/sources/teavmc-classpath/resources/\" to \"" + srcTeavmCRes.getAbsolutePath() + "\"!");
				throw ex;
			}
		}

		if(repoSourcesBootMenu.isDirectory()) {
			System.out.println("Copying files from \"/sources/teavm-boot-menu/java/\" to workspace...");
			
			try {
				if(!srcBootMenu.isDirectory() && !srcBootMenu.mkdirs()) {
					System.err.println("ERROR: Could not create destination directory!");
					return false;
				}
				FileUtils.copyDirectory(repoSourcesBootMenu, srcBootMenu);
			}catch(IOException ex) {
				System.err.println("ERROR: could not copy \"/sources/teavm-boot-menu/java/\" to \"" + srcBootMenu.getAbsolutePath() + "\"!");
				throw ex;
			}
		}

		if(repoSourcesLWJGL.isDirectory()) {
			System.out.println("Copying files from \"/sources/lwjgl/java/\" to workspace...");
			
			try {
				if(!srcLWJGLJava.isDirectory() && !srcLWJGLJava.mkdirs()) {
					System.err.println("ERROR: Could not create destination directory!");
					return false;
				}
				FileUtils.copyDirectory(repoSourcesLWJGL, srcLWJGLJava);
			}catch(IOException ex) {
				System.err.println("ERROR: could not copy \"/sources/lwjgl/java/\" to \"" + srcLWJGLJava.getAbsolutePath() + "\"!");
				throw ex;
			}
		}

		if(repoSourcesWASMGCTeaVMJava.isDirectory()) {
			System.out.println("Copying files from \"/sources/wasm-gc-teavm/java/\" to workspace...");
			
			try {
				if(!srcWASMGCTeaVMJava.isDirectory() && !srcWASMGCTeaVMJava.mkdirs()) {
					System.err.println("ERROR: Could not create destination directory!");
					return false;
				}
				FileUtils.copyDirectory(repoSourcesWASMGCTeaVMJava, srcWASMGCTeaVMJava);
			}catch(IOException ex) {
				System.err.println("ERROR: could not copy \"/sources/wasm-gc-teavm/java/\" to \"" + srcWASMGCTeaVMJava.getAbsolutePath() + "\"!");
				throw ex;
			}
		}

		if(repoSourcesWASMGCTeaVMJS.isDirectory()) {
			System.out.println("Copying files from \"/sources/wasm-gc-teavm/js/\" to workspace...");
			
			try {
				if(!srcWASMGCTeaVMJS.isDirectory() && !srcWASMGCTeaVMJS.mkdirs()) {
					System.err.println("ERROR: Could not create destination directory!");
					return false;
				}
				FileUtils.copyDirectory(repoSourcesWASMGCTeaVMJS, srcWASMGCTeaVMJS);
			}catch(IOException ex) {
				System.err.println("ERROR: could not copy \"/sources/wasm-gc-teavm/js/\" to \"" + srcWASMGCTeaVMJS.getAbsolutePath() + "\"!");
				throw ex;
			}
		}

		if(repoSourcesWASMGCTeaVMBootstrapJS.isDirectory()) {
			System.out.println("Copying files from \"/sources/wasm-gc-teavm-bootstrap/js/\" to workspace...");
			
			try {
				if(!srcWASMGCTeaVMBootstrapJS.isDirectory() && !srcWASMGCTeaVMBootstrapJS.mkdirs()) {
					System.err.println("ERROR: Could not create destination directory!");
					return false;
				}
				FileUtils.copyDirectory(repoSourcesWASMGCTeaVMBootstrapJS, srcWASMGCTeaVMBootstrapJS);
			}catch(IOException ex) {
				System.err.println("ERROR: could not copy \"/sources/wasm-gc-teavm-bootstrap/js/\" to \"" + srcWASMGCTeaVMBootstrapJS.getAbsolutePath() + "\"!");
				throw ex;
			}
		}

		if(repoSourcesWASMGCTeaVMLoaderC.isDirectory()) {
			System.out.println("Copying files from \"/sources/wasm-gc-teavm-loader/c/\" to workspace...");
			
			try {
				if(!srcWASMGCTeaVMLoaderC.isDirectory() && !srcWASMGCTeaVMLoaderC.mkdirs()) {
					System.err.println("ERROR: Could not create destination directory!");
					return false;
				}
				FileUtils.copyDirectory(repoSourcesWASMGCTeaVMLoaderC, srcWASMGCTeaVMLoaderC);
			}catch(IOException ex) {
				System.err.println("ERROR: could not copy \"/sources/wasm-gc-teavm-loader/c/\" to \"" + srcWASMGCTeaVMLoaderC.getAbsolutePath() + "\"!");
				throw ex;
			}
		}

		if(repoSourcesWASMGCTeaVMLoaderJS.isDirectory()) {
			System.out.println("Copying files from \"/sources/wasm-gc-teavm-loader/js/\" to workspace...");
			
			try {
				if(!srcWASMGCTeaVMLoaderJS.isDirectory() && !srcWASMGCTeaVMLoaderJS.mkdirs()) {
					System.err.println("ERROR: Could not create destination directory!");
					return false;
				}
				FileUtils.copyDirectory(repoSourcesWASMGCTeaVMLoaderJS, srcWASMGCTeaVMLoaderJS);
			}catch(IOException ex) {
				System.err.println("ERROR: could not copy \"/sources/wasm-gc-teavm-loader/js/\" to \"" + srcWASMGCTeaVMLoaderJS.getAbsolutePath() + "\"!");
				throw ex;
			}
		}
		
		System.out.println("Copying files from \"/sources/resources/\" to workspace...");
		
		try {
			if(!resourcesExtractTo.isDirectory() && !resourcesExtractTo.mkdirs()) {
				System.err.println("ERROR: Could not create destination directory!");
				return false;
			}
			FileUtils.copyDirectory(repoSourcesResources, resourcesExtractTo);
		}catch(IOException ex) {
			System.err.println("ERROR: could not copy \"/sources/resources/\" to \"" + resourcesExtractTo.getAbsolutePath() + "\"!");
			throw ex;
		}
		
		if(applyPullRequest) {
			System.out.println();
			System.out.println("Applying \"pullrequest\" directory to \"minecraft_src_patch.jar\"...");

			File unpatchOut = new File(mcTmpDirectory, "minecraft_src.jar");
			File patchOut = new File(mcTmpDirectory, "minecraft_src_patch.jar");
			File unpatchResOut = new File(mcTmpDirectory, "minecraft_res.jar");
			File patchResOut = new File(mcTmpDirectory, "minecraft_res_patch.jar");
			File tmpPatchedPatchOut = new File(mcTmpDirectory, "minecraft_src_pullrequest_patch.jar");
			File tmpPatchedPatchJavadocOut = new File(mcTmpDirectory, "minecraft_src_pullrequest_javadoc.jar");
			File tmpPatchedPatchResOut = new File(mcTmpDirectory, "minecraft_res_pullrequest_patch.jar");
			
			try {
				ApplyPatchesToZip.applyPatches(patchOut, unpatchOut, new File("./pullrequest/source"), tmpPatchedPatchOut, false, false);
			}catch(Throwable t) {
				System.err.println();
				System.err.println("ERROR: Could not apply pullrequest directory patches to: " + patchOut.getName());
				System.err.println(t.toString());
				tmpPatchedPatchOut.delete();
				return false;
			}
			
			CSVMappings comments = new CSVMappings();
			if(!InsertJavaDoc.processSource(tmpPatchedPatchOut, tmpPatchedPatchJavadocOut, 
					new File(btTmpDirectory, "ModCoderPack"), comments)) {
				System.err.println();
				System.err.println("ERROR: Could not create pullrequest javadoc!");
				return false;
			}
			
			tmpPatchedPatchOut.delete();
			
			try {
				ApplyPatchesToZip.applyPatches(patchResOut, unpatchResOut, new File("./pullrequest/resources"), tmpPatchedPatchResOut, false, false);
			}catch(Throwable t) {
				System.err.println();
				System.err.println("ERROR: Could not apply pullrequest directory patches to: " + patchResOut.getName());
				System.err.println(t.toString());
				tmpPatchedPatchOut.delete();
				tmpPatchedPatchResOut.delete();
				return false;
			}

			minecraftJavadocTmp = tmpPatchedPatchJavadocOut;
			minecraftResJar = tmpPatchedPatchResOut;
			
		}else {
			System.out.println("Extracting files from \"minecraft_src_javadoc.jar\" to \"/src/game/java/\"...");
		}
			
		try {
			if(!srcGameJava.isDirectory() && !srcGameJava.mkdirs()) {
				System.err.println("ERROR: Could not create destination directory!");
				return false;
			}
			extractJarTo(minecraftJavadocTmp, srcGameJava);
		}catch(IOException ex) {
			System.err.println("ERROR: could not extract \"" + minecraftJavadocTmp.getName() + ".jar\" to \"" +
					srcGameJava.getAbsolutePath() + "\"!");
			throw ex;
		}
		
		System.out.println("Extracting files from \"minecraft_res_patch.jar\" to \"/desktopRuntime/resources/\"...");
		
		try {
			extractJarTo(minecraftResJar, resourcesExtractTo);
		}catch(IOException ex) {
			System.err.println("ERROR: could not extract \"" + minecraftResJar.getName() + "\" to \"" + 
					resourcesExtractTo.getAbsolutePath() + "\"!");
			throw ex;
		}
		
		if(applyPullRequest) {
			minecraftJavadocTmp.delete();
			minecraftResJar.delete();
		}
		
		System.out.println("Extracting files from \"minecraft_languages.zip\" to \"/target_teavm_javascript/javascript/lang/\"...");
		
		try {
			extractJarTo(mcLanguagesZip, mcLanguagesExtractTo);
		}catch(IOException ex) {
			System.err.println("ERROR: could not extract \"" + mcLanguagesZip.getName() + "\" to \"" + 
					mcLanguagesExtractTo.getAbsolutePath() + "\"!");
			throw ex;
		}
		
		System.out.println("Extracting files from \"local-libs.zip\" to \"/gradle/local-libs/\"...");
		
		try {
			extractJarTo(localLibsZip, localLibsExtractTo);
		}catch(IOException ex) {
			System.err.println("ERROR: could not extract \"" + localLibsZip.getName() + "\" to \"" + 
					localLibsExtractTo.getAbsolutePath() + "\"!");
			throw ex;
		}
		
//		System.out.println("Creating eclipse project for desktop runtime...");
//		if(!createDesktopRuntimeProject(new File(repoSources, "setup/eclipseProjectFiles"), workspaceDirectory)) {
//			System.err.println("ERROR: could not create eclipse project for desktop runtime!");
//			return false;
//		}
		
		return true;
	}
	
	public static int extractJarTo(File in, File out) throws IOException {
		int cnt = 0;
		try(ZipInputStream jarIn = new ZipInputStream(new FileInputStream(in))) {
			ZipEntry e;
			while((e = jarIn.getNextEntry()) != null) {
				if(e.isDirectory()) {
					continue;
				}
				String n = e.getName();
				if(n.startsWith("/")) {
					n = n.substring(1);
				}
				if(!n.startsWith("META-INF")) {
					File o = new File(out, n);
					if(!o.exists()) {
						File p = o.getParentFile();
						if(!p.isDirectory() && !p.mkdirs()) {
							throw new IOException("Could not create directory: " + p.getAbsolutePath());
						}
						try(FileOutputStream os = new FileOutputStream(o)) {
							IOUtils.copy(jarIn, os, 4096);
							++cnt;
						}
					}
				}
			}
		}
		return cnt;
	}
	
	@Deprecated
	private static boolean createDesktopRuntimeProject(File templateFolderIn, File workspaceDirectory) throws Throwable {
		File desktopRuntimeDirectory = new File(workspaceDirectory, "desktopRuntime");
		File desktopRuntimeProjectDir = new File(desktopRuntimeDirectory, "eclipseProject");
		if(!desktopRuntimeProjectDir.isDirectory() && !desktopRuntimeProjectDir.mkdirs()) {
			System.err.println("ERROR: failed to create directory: \"" + desktopRuntimeProjectDir.getAbsolutePath() + "\"!");
			return false;
		}
		File binFolder = new File(desktopRuntimeProjectDir, "bin");
		if(!binFolder.isDirectory() && !binFolder.mkdir()) {
			System.err.println("ERROR: failed to create directory: \"" + binFolder.getAbsolutePath() + "\"!");
			return false;
		}
		String dotClasspathFile = FileUtils.readFileToString(new File(templateFolderIn, ".classpath"), "UTF-8");
		String dotClasspathEntryFile = FileUtils.readFileToString(new File(templateFolderIn, "classpath_entry.txt"), "UTF-8");
		String dotProjectFile = FileUtils.readFileToString(new File(templateFolderIn, ".project"), "UTF-8");
		String debugRuntimeLaunchConfig = FileUtils.readFileToString(new File(templateFolderIn, "eaglercraftDebugRuntime.launch"), "UTF-8");
		String mainClassConfFile = FileUtils.readFileToString(new File(templateFolderIn, "main_class.txt"), "UTF-8");
		
		List<String> classpathEntries = new ArrayList();
		File[] flist = desktopRuntimeDirectory.listFiles();
		for(int i = 0; i < flist.length; ++i) {
			File f = flist[i];
			if(f.getName().endsWith(".jar")) {
				classpathEntries.add(dotClasspathEntryFile.replace("${JAR_PATH}", bsToS(f.getAbsolutePath())));
			}
		}
		
		dotClasspathFile = dotClasspathFile.replace("${LIBRARY_CLASSPATH}", String.join(System.lineSeparator(), classpathEntries));
		FileUtils.writeStringToFile(new File(desktopRuntimeProjectDir, ".classpath"), dotClasspathFile, "UTF-8");
		
		dotProjectFile = dotProjectFile.replace("${MAIN_SRC_FOLDER}", bsToS((new File(workspaceDirectory, "src/main/java")).getAbsolutePath()));
		dotProjectFile = dotProjectFile.replace("${GAME_SRC_FOLDER}", bsToS((new File(workspaceDirectory, "src/game/java")).getAbsolutePath()));
		dotProjectFile = dotProjectFile.replace("${PROTO_GAME_SRC_FOLDER}", bsToS((new File(workspaceDirectory, "src/protocol-game/java")).getAbsolutePath()));
		dotProjectFile = dotProjectFile.replace("${PROTO_RELAY_SRC_FOLDER}", bsToS((new File(workspaceDirectory, "src/protocol-relay/java")).getAbsolutePath()));
		dotProjectFile = dotProjectFile.replace("${LWJGL_SRC_FOLDER}", bsToS((new File(workspaceDirectory, "src/lwjgl/java")).getAbsolutePath()));
		FileUtils.writeStringToFile(new File(desktopRuntimeProjectDir, ".project"), dotProjectFile, "UTF-8");
		
		debugRuntimeLaunchConfig = debugRuntimeLaunchConfig.replace("${MAIN_CLASS_FILE}", mainClassConfFile);
		
		String mainClassSubstr = mainClassConfFile.substring(mainClassConfFile.indexOf('/') + 1);
		if(mainClassSubstr.endsWith(".java")) {
			mainClassSubstr = mainClassSubstr.substring(0, mainClassSubstr.length() - 5);
		}
		mainClassSubstr = mainClassSubstr.replace('/', '.');
		debugRuntimeLaunchConfig = debugRuntimeLaunchConfig.replace("${MAIN_CLASS_NAME}", mainClassSubstr);
		debugRuntimeLaunchConfig = debugRuntimeLaunchConfig.replace("${WORKING_DIRECTORY}", bsToS(desktopRuntimeDirectory.getAbsolutePath()));
		FileUtils.writeStringToFile(new File(desktopRuntimeProjectDir, "eaglercraftDebugRuntime.launch"), debugRuntimeLaunchConfig, "UTF-8");
		
		File dotSettingsPrefFile = new File(templateFolderIn, "org.eclipse.jdt.core.prefs");
		File destDotSettingsFolder = new File(desktopRuntimeProjectDir, ".settings");
		if(!destDotSettingsFolder.isDirectory() && !destDotSettingsFolder.mkdir()) {
			System.err.println("ERROR: failed to create directory: \"" + destDotSettingsFolder.getAbsolutePath() + "\"!");
			return false;
		}
		
		FileUtils.copyFile(dotSettingsPrefFile, new File(destDotSettingsFolder, "org.eclipse.jdt.core.prefs"));
		
		return true;
	}
	
	private static String bsToS(String in) {
		return in.replace('\\', '/');
	}
	
}
