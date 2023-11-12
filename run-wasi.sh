mkdir -p target/build/wasi-testdir
wasmtime run --dir target/build/wasi-testdir::/ $1 $2
