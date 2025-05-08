#!/bin/sh

cd wasm_gc_teavm
. ./CompileEPK.sh
. ./CompileWASM.sh
. ./CompileEagRuntimeJS.sh
. ./MakeWASMClientBundle.sh