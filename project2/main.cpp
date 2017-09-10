#include <iostream>
#include <cmath>
#include <GL/glut.h>
using namespace std;

// Resolution of the screen
float width	= 0;
float height 	= 0;

// Prototypes of main functions
void drawLine(float x1, float y1, float x2, float y2);
void createWindow(int argc, char* argv[]);
void reshape(int w, int h);
void display();

int main(int argc, char* argv[]) {

	createWindow(argc, argv);

	cout << "Hello world" << endl;
	return 0;
}

void createWindow(int argc, char* argv[]) {
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DOUBLE|GLUT_RGBA);
	glutInitWindowSize(800, 600);
	glutCreateWindow("Vadim Gush");
	glutReshapeFunc(reshape);
	glutDisplayFunc(display);
	glutMainLoop();

}

void display() {
	glClear(GL_COLOR_BUFFER_BIT);

	drawLine(0,0,1,1);

	glutSwapBuffers();
}

void reshape(int w, int h) {
	width = w;
	height = h;

	glViewport(0,0,w,h);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluOrtho2D(0,w,0,h);
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
}

void drawLine(float x1, float y1, float x2, float y2) {
	glBegin(GL_LINES);
	glColor3f(1,1,1);
	glVertex2f(x1 * width, y1 * height);
	glVertex2f(x2 * width, y2 * height);
	glEnd();
}
