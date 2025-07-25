/*
 * Copyright (c) 2024 lax1dude. All Rights Reserved.
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

package net.lax1dude.eaglercraft.v1_8.internal.teavm.opts;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSObject;
import org.teavm.jso.core.JSArrayReader;

public abstract class JSEaglercraftXOptsRoot implements JSObject {

	@JSBody(script = "return (typeof this.container === \"string\") ? this.container : null;")
	public native String getContainer();

	@JSBody(script = "return (typeof this.assetsURI === \"string\") ? this.assetsURI : null;")
	public native String getAssetsURI();

	@JSBody(script = "return (typeof this.assetsURI === \"object\") ? this.assetsURI : null;")
	public native JSArrayReader<JSEaglercraftXOptsAssetsURI> getAssetsURIArray();

	@JSBody(params = { "def" }, script = "return (typeof this.lang === \"string\") ? this.lang : def;")
	public native String getLang(String defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.joinServer === \"string\") ? this.joinServer : def;")
	public native String getJoinServer(String defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.localesURI === \"string\") ? this.localesURI : def;")
	public native String getLocalesURI(String defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.worldsDB === \"string\") ? this.worldsDB : def;")
	public native String getWorldsDB(String defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.resourcePacksDB === \"string\") ? this.resourcePacksDB : def;")
	public native String getResourcePacksDB(String defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.demoMode === \"boolean\") ? this.demoMode : def;")
	public native boolean getDemoMode(boolean defaultValue);

	@JSBody(script = "return (typeof this.servers === \"object\") ? this.servers : null;")
	public native JSArrayReader<JSEaglercraftXOptsServer> getServers();

	@JSBody(script = "return (typeof this.relays === \"object\") ? this.relays : null;")
	public native JSArrayReader<JSEaglercraftXOptsRelay> getRelays();

	@JSBody(params = { "def" }, script = "return (typeof this.checkGLErrors === \"boolean\") ? this.checkGLErrors : def;")
	public native boolean getCheckGLErrors(boolean defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.checkShaderGLErrors === \"boolean\") ? this.checkShaderGLErrors : def;")
	public native boolean getCheckShaderGLErrors(boolean defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.enableDownloadOfflineButton === \"boolean\") ? this.enableDownloadOfflineButton : def;")
	public native boolean getEnableDownloadOfflineButton(boolean defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.downloadOfflineButtonLink === \"string\") ? this.downloadOfflineButtonLink : def;")
	public native String getDownloadOfflineButtonLink(String defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.html5CursorSupport === \"boolean\") ? this.html5CursorSupport : def;")
	public native boolean getHtml5CursorSupport(boolean defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.allowUpdateSvc === \"boolean\") ? this.allowUpdateSvc : def;")
	public native boolean getAllowUpdateSvc(boolean defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.allowUpdateDL === \"boolean\") ? this.allowUpdateDL : def;")
	public native boolean getAllowUpdateDL(boolean defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.logInvalidCerts === \"boolean\") ? this.logInvalidCerts : def;")
	public native boolean getLogInvalidCerts(boolean defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.enableSignatureBadge === \"boolean\") ? this.enableSignatureBadge : def;")
	public native boolean getEnableSignatureBadge(boolean defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.checkRelaysForUpdates === \"boolean\") ? this.checkRelaysForUpdates : def;")
	public native boolean getCheckRelaysForUpdates(boolean defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.allowVoiceClient === \"boolean\") ? this.allowVoiceClient : def;")
	public native boolean getAllowVoiceClient(boolean defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.allowFNAWSkins === \"boolean\") ? this.allowFNAWSkins : def;")
	public native boolean getAllowFNAWSkins(boolean defaultValue);

	@JSBody(script = "return (typeof this.hooks === \"object\") ? this.hooks : null;")
	public native JSEaglercraftXOptsHooks getHooks();

	@JSBody(params = { "def" }, script = "return (typeof this.localStorageNamespace === \"string\") ? this.localStorageNamespace : def;")
	public native String getLocalStorageNamespace(String defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.enableMinceraft === \"boolean\") ? this.enableMinceraft : def;")
	public native boolean getEnableMinceraft(boolean defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.enableServerCookies === \"boolean\") ? this.enableServerCookies : def;")
	public native boolean getEnableServerCookies(boolean defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.allowServerRedirects === \"boolean\") ? this.allowServerRedirects : def;")
	public native boolean getAllowServerRedirects(boolean defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.crashOnUncaughtExceptions === \"boolean\") ? this.crashOnUncaughtExceptions : def;")
	public native boolean getCrashOnUncaughtExceptions(boolean defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.openDebugConsoleOnLaunch === \"boolean\") ? this.openDebugConsoleOnLaunch : def;")
	public native boolean getOpenDebugConsoleOnLaunch(boolean defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.fixDebugConsoleUnloadListener === \"boolean\") ? this.fixDebugConsoleUnloadListener : def;")
	public native boolean getFixDebugConsoleUnloadListener(boolean defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.forceWebViewSupport === \"boolean\") ? this.forceWebViewSupport : def;")
	public native boolean getForceWebViewSupport(boolean defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.enableWebViewCSP === \"boolean\") ? this.enableWebViewCSP : def;")
	public native boolean getEnableWebViewCSP(boolean defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.autoFixLegacyStyleAttr === \"boolean\") ? this.autoFixLegacyStyleAttr : def;")
	public native boolean getAutoFixLegacyStyleAttr(boolean defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.showBootMenuOnLaunch === \"boolean\") ? this.showBootMenuOnLaunch : def;")
	public native boolean getShowBootMenuOnLaunch(boolean defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.bootMenuBlocksUnsignedClients === \"boolean\") ? this.bootMenuBlocksUnsignedClients : def;")
	public native boolean getBootMenuBlocksUnsignedClients(boolean defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.allowBootMenu === \"boolean\") ? this.allowBootMenu : def;")
	public native boolean getAllowBootMenu(boolean defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.forceProfanityFilter === \"boolean\") ? this.forceProfanityFilter : def;")
	public native boolean getForceProfanityFilter(boolean defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.forceWebGL1 === \"boolean\") ? this.forceWebGL1 : def;")
	public native boolean getForceWebGL1(boolean defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.forceWebGL2 === \"boolean\") ? this.forceWebGL2 : def;")
	public native boolean getForceWebGL2(boolean defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.allowExperimentalWebGL1 === \"boolean\") ? this.allowExperimentalWebGL1 : def;")
	public native boolean getAllowExperimentalWebGL1(boolean defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.useWebGLExt === \"boolean\") ? this.useWebGLExt : def;")
	public native boolean getUseWebGLExt(boolean defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.useDelayOnSwap === \"boolean\") ? this.useDelayOnSwap : def;")
	public native boolean getUseDelayOnSwap(boolean defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.useJOrbisAudioDecoder === \"boolean\") ? this.useJOrbisAudioDecoder : def;")
	public native boolean getUseJOrbisAudioDecoder(boolean defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.useXHRFetch === \"boolean\") ? this.useXHRFetch : def;")
	public native boolean getUseXHRFetch(boolean defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.useVisualViewport === \"boolean\") ? this.useVisualViewport : def;")
	public native boolean getUseVisualViewport(boolean defaultValue);

	@JSBody(params = { "def" }, script = "return (typeof this.deobfStackTraces === \"boolean\") ? this.deobfStackTraces : def;")
	public native boolean getDeobfStackTraces(boolean deobfStackTraces);

	@JSBody(params = { "def" }, script = "return (typeof this.disableBlobURLs === \"boolean\") ? this.disableBlobURLs : def;")
	public native boolean getDisableBlobURLs(boolean deobfStackTraces);

	@JSBody(params = { "def" }, script = "return (typeof this.eaglerNoDelay === \"boolean\") ? this.eaglerNoDelay : def;")
	public native boolean getEaglerNoDelay(boolean deobfStackTraces);

	@JSBody(params = { "def" }, script = "return (typeof this.ramdiskMode === \"boolean\") ? this.ramdiskMode : def;")
	public native boolean getRamdiskMode(boolean deobfStackTraces);

	@JSBody(params = { "def" }, script = "return (typeof this.singleThreadMode === \"boolean\") ? this.singleThreadMode : def;")
	public native boolean getSingleThreadMode(boolean deobfStackTraces);

	@JSBody(params = { "def" }, script = "return (typeof this.enableEPKVersionCheck === \"boolean\") ? this.enableEPKVersionCheck : def;")
	public native boolean getEnableEPKVersionCheck(boolean deobfStackTraces);

	@JSBody(params = { "def" }, script = "return (typeof this.keepAliveHack === \"boolean\") ? this.keepAliveHack : def;")
	public native boolean getKeepAliveHack(boolean keepAliveHack);

	@JSBody(params = { "def" }, script = "return (typeof this.finishOnSwap === \"boolean\") ? this.finishOnSwap : def;")
	public native boolean getFinishOnSwap(boolean finishOnSwap);

}