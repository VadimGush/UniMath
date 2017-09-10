#!/bin/bash

# Small script for compiling and running program
clear; g++ -lglut -lGL -lGLU -lGLEW main.cpp -o program && ./program
