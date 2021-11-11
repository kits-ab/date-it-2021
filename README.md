Samuel Kyletoft
skyletoft@gmail.com
Yeah, I'm not publishing my phone number on github.
Email spam can be filtered, phone spam is a pain

Java för att uppgiften sa java, Rust för att det är roligare att skriva

Rustresultat
```bash
$ cargo build --release && hyperfine "target/release/rust_rewrite ../kommuner.csv ../skolverksamhet.csv > /dev/null" -w20 -m200
    Finished release [optimized] target(s) in 0.00s
Benchmark 1: target/release/rust_rewrite ../kommuner.csv ../skolverksamhet.csv > /dev/null
  Time (mean ± σ):       7.9 ms ±   1.1 ms    [User: 4.9 ms, System: 3.0 ms]
  Range (min … max):     5.5 ms …  10.7 ms    267 runs
  ```

Javaresultat
```bash
$ javac Main.java && hyperfine "java Main kommuner.csv skolverksamhet.csv > /dev/null" -w20 -m200
Benchmark 1: java Main kommuner.csv skolverksamhet.csv > /dev/null
  Time (mean ± σ):     121.1 ms ±  11.4 ms    [User: 201.8 ms, System: 23.6 ms]
  Range (min … max):   102.2 ms … 166.1 ms    200 runs
```
