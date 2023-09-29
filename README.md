# DeV Tools / DVT - The Swiss Army Knife of CLI utilities

`dvt` aims to bundle all small utilities used by developers (typically a mix of cli and
online tools) into one binary that you can simply use in the console. No need for complex pipe-ing, 
copy-pasting on different sites or keep installing cli utilities for every need. 

# How to use dvt

```shell
➜ dvt  -h

dvt - command line dev tools; version 1.0.1

Usage: dvt [-hV] [COMMAND]
  -h, --help      Show this help message and exit.
  -V, --version   Print version information and exit.
Commands:
  generate-completion  Generate bash/zsh completion script for dvt.
  base64               Encode/decode strings according to RFC 2045
  case                 Convert strings to different cases
  random               Generate random data based on the supplied data type
  hash                 Perform hashing operations
  jwt                  Verify and decode JWTs
  json                 Manipulate JSON files
  html                 Manipulate HTML files
```

# Installation

## Homebrew

```bash
brew install ludovicianul/tap/dvt
```

## Manually download binaries

`dvt` is compiled to native code using GraalVM. Check
the [release page](https://github.com/ludovicianul/dvt/releases/tag/dvt-1.0.1) for binaries (Linux, MacOS, uberjar).

After download, you can make `dvt` globally available:

```bash
sudo cp dvt-macos /usr/local/bin/dvt
```

The uberjar can be run using `java -jar dvt-uberjar`. Requires Java 11+.

# Autocomplete
Run the following commands to get autocomplete:

```bash
dvt generate-completion >> dvt_autocomplete

source dvt_autocomplete
```
