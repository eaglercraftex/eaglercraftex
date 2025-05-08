@echo off

cd wasm_gc_teavm
call CompileEPK.bat
call CompileWASM.bat
call CompileEagRuntimeJS.bat
call MakeWASMClientBundle.bat