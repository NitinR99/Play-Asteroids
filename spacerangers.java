import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
public class SpaceRangers extends JFrame implements KeyListener {
public static void main () throws Exception{
System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
JFrame o1=new JFrame();
o1.setBackground(Color.WHITE);
// adjust to need.
o1.setPreferredSize(new Dimension(1200,800));
o1.setResizable(false);
o1.setVisible(true);
SpaceRangers gg=new SpaceRangers();
o1.add(gg);
}
static int score = 0;
static boolean fire2 = false;
static boolean wmd = false;
static int deltax[] = {0,-1, 1, 0};
static int deltay[] = {-1, 0, 0, 1};
static int rand[] = { (int) (Math.floor(Math.random()*8+1)), (int) (Math.floor(Math.random()*8+1)), (int) (Math.floor(Math.random()*8+1)), (int) (Math.floor(Math.random()*8+1)), (int) (Math.floor(Math.random()*8+1)), (int) (Math.floor(Math.random()*8+1)), (int) Math.floor(Math.random()*8+1), (int) Math.floor(Math.random()*8+1), (int) (Math.floor(Math.random()*8+1)) };
static Color[] colour = { Color.WHITE,Color.red};
static Color[][] grid = new Color[14][23];
static int energy = 0;
static double pulse = 0;
static double bullet = 0;
static double clock = 0;
static boolean fire = false;
static boolean up = false;
static boolean down = false;
static boolean left = false;
static boolean right = false;
static int m = 16;
static int n = 26;
JLabel b[][];
static int health = 15;
static int centrex = 7;
static int centrey = 19;
static int ammo = 15;
static boolean alive = true;
static int num = 0;
static Color[] bc = new Color[25];
static int[] bx = new int[25];
static int[] by = new int[25];
static int tmp[][] = new int[14][23];
static int count = 0;
public SpaceRangers() throws Exception{
for (int x = 0;x<25;x++){
bx[x] = -5;
by[x] = -5;
}
this.setDefaultCloseOperation(EXIT_ON_CLOSE);
this.setTitle("Space Rangers v1.0");
b = new JLabel [m][n];
setLayout(new GridLayout(n,m));
for (int y = 0;y<n;y++){
for (int x = 0;x<m;x++){
b[x][y] = new JLabel( );
b[x][y].setBackground(Color.BLACK);
add(b[x][y]);
b[x][y].setEnabled(true);
b[x][y].setOpaque(true);
b[x][y].setPreferredSize(new Dimension(50,25));
}
}
setFocusable(true);
addKeyListener(this);
pack();
setVisible(true);
go();
}
public void combo(){
for (int x = 0;x<12;x++){
for (int y = 0;y<21;y++){
if (grid[x+1][y+1] != Color.BLACK){
count = 0;
combochk(x, y, grid[x+1][y+1]);
for (int x2 = 0;x2<12;x2++){
for (int y2 = 0;y2<21;y2++){
if (count > 0){
if (tmp[x2+1][y2+1] == 1){
grid[x2+1][y2+1] = Color.BLACK;
}
}
tmp[x2+1][y2+1] = 0;
}
}
if (count > 0){
score++;
}
}}
}
}
public void combochk(int x, int y, Color c){
for (int a = 0;a<4;a++){
if ((grid[x+1+deltax[a]][y+1+deltay[a]] == c) && (tmp[x+deltax[a]+1][y+deltay[a]+1] == 0)){
tmp[x+deltax[a]+1][y+deltay[a]+1] = 1;
count++;
combochk(x+deltax[a], y+deltay[a], c);
}}}
public void gen(){
int randi;
int geni;
for (int x = 0;x<12;x++){
for (int y = 19;y>=0;y--){
grid[x+1][y+1+1] = grid[x+1][y+1];
}
}
do{
geni = (int) (Math.random()*10+1);
randi = (int) (Math.random()*10+1);
} while(!(geni<=13) &&!(randi<=22));
grid [randi][geni] = colour[0];
}
public void match(){
for (int x = 0;x<12;x++){
for (int y = 0;y<21;y++){
b[x+2][y].setBackground(grid[x+1][y+1]);
}
}
}
public void go() throws Exception{
setup();
int time = 1000;
clock = System.currentTimeMillis();
bullet = System.currentTimeMillis();
pulse = System.currentTimeMillis();
do{
if (health == 0){
JFrame tabule=new JFrame();
Font font = new Font("Lucida Console", Font.BOLD, 25);
JLabel textLabel = new JLabel(" The Ship crashed. ");
textLabel.setFont(font);
textLabel.setBounds(10,10,600,35);
tabule.add(textLabel);
JLabel sc = new JLabel(" Score: "+score);
sc.setFont(font);
sc.setBounds(15,80,600,35);
ImageIcon icon = new ImageIcon("C:/pics/abcd.gif");
JLabel copyLabel = new JLabel(icon);
copyLabel.setBounds(190,240,120,130);
tabule.add(copyLabel);
tabule.add(sc);
tabule.setSize(530, 420);
tabule.setBackground(Color.WHITE);
tabule.setTitle(" GAME OVER");
tabule.setLocationRelativeTo(null);
tabule.setLayout(null);
tabule.setVisible(true);
JButton retry=new JButton("Exit");
retry.setBounds(110,120,300,25);
retry.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent e) {
try{
System.exit(0);}
catch(Exception ex)
{}
}
});
JButton gonsave=new JButton("Save score");
gonsave.setBounds(110,150,300,25);
gonsave.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent e) {
try{
SaveOption o1=new SaveOption();
o1.OpenSave();
o1.letsSave(score);
}
catch(Exception ex)
{}
}
});
tabule.add(retry);
tabule.add(gonsave);
break;
}
ship();
if (System.currentTimeMillis()-pulse > time){
if (time>100){
time-=10;
}
gen();
match();
pulse = System.currentTimeMillis();
}
if (System.currentTimeMillis()-clock > 30){
regen();
clock = System.currentTimeMillis();
}
if (System.currentTimeMillis()-bullet > 30){
health();
combo();
projectile();
bullet = System.currentTimeMillis();
}
if (up == true){
up();
up = false;
}
if (down == true){
down();
down = false;
}
if (left == true){
left();
left = false;
}
if (right == true){
right();
right = false;
}
if (fire == true){
if ((ammo>0) && (num<25)){
fire();
projectile();
}
}
if (fire2 == true){
wmd();
fire2 = false;
}
fire = false;
}while (alive == true);
}
public void wmd(){
energy = 0;
for (int x = 0;x<12;x++){
for (int y = 0;y<21;y++){
grid[x+1][y+1] = Color.BLACK;
}
}
}
public void regen(){
if (ammo<12){
ammo+=1;
ammo(Color.blue);
} else {
ammo = 15;
ammo(Color.blue);
}
}
public void setup(){
for (int x = 1;x<13;x++){
for(int y = 1;y<22;y++){
grid[x][y] = Color.BLACK;
}
}
ship();
health();
ammo(Color.blue);
gen();
match();
}
public void ammo(Color l){
for (int x = 1;x<ammo;x++){
b[x][25].setBackground(l);
b[x][25].setBackground(l);
}
}
public void health(){
for (int x = 1;x<15;x++){
b[x][24].setBackground(Color.BLACK);
}
for (int x = 1;x<health;x++){
if (x<3){
b[x][24].setBackground(Color.green);
} else {
b[x][24].setBackground(Color.green);
}
}
}
public void ship(){
b[centrex+1][centrey].setBackground(Color.GRAY);
b[centrex-1][centrey].setBackground(Color.GRAY);
b[centrex][centrey-1].setBackground(Color.GRAY);
b[centrex][centrey-2].setBackground(Color.GRAY);
b[centrex+1][centrey-1].setBackground(Color.GRAY);
b[centrex-1][centrey-1].setBackground(Color.GRAY);
}
public void fire(){
num++;
bx[num-1] = centrex;
by[num-1] = centrey-3;
bc[num-1] = colour[0];
ammo(Color.orange);
if (ammo>-1){
ammo-=6;
}
ammo(Color.blue);
}
public void projectile(){
boolean hit = false;
Color tmpbc[] = new Color[25];
int tmpbx[] = new int[25];
int tmpby[] = new int[25];
for (int y = 0;y<25;y++){
tmpbc[y] = bc[y];
tmpbx[y] = bx[y];
tmpby[y] = by[y];
}
for (int x = 0;x<num;x++){
if ((by[x] > 0) && (checkhit(bx[x],by[x]) == false)){
b[bx[x]][by[x]].setBackground(Color.red);
} else {
hit = true;
if (hit == true){
grid[bx[x]-2+1][by[x]+1] = bc[x];
if(x < 25){
if (x != 24){
if (tmpbx[x+1] == -5){
tmpbc[x] = null;
tmpbx[x] = -5;
tmpby[x] = -5;
} else {
for (int c = x;c<num;c++){
tmpbc[c] = tmpbc[c+1];
tmpbx[c] = tmpbx[c+1];
tmpby[c] = tmpby[c+1];
}
}
} else {
tmpbc[x] = null;
tmpbx[x] = -5;
tmpby[x] = -5;
}
num--;
}
x--;
for (int y = 0;y<25;y++){
bc[y] = tmpbc[y];
bx[y] = tmpbx[y];
by[y] = tmpby[y];
}
}
}
}
try {
Thread.sleep(100);
} catch (Exception e) {
}
for (int x = 0;x<num;x++){
if ((by[x] > 0) && (checkhit(bx[x],by[x]) == false)){
by[x]--;
b[bx[x]][by[x]].setBackground(bc[x]);
}
}
}
public boolean checkhit(int bx, int by){
if((b[bx][by-1].getBackground() == Color.BLACK)){
return false;
} else{
return true;
}
}
public void right(){
if ((b[centrex+1+1][centrey].getBackground() != Color.BLACK) ||
(b[centrex+1+1][centrey-1].getBackground() != Color.BLACK) ||
(b[centrex+1][centrey-1-1].getBackground() != Color.BLACK)){
health--;
}
b[centrex+1][centrey].setBackground(Color.BLACK);
b[centrex-1][centrey].setBackground(Color.BLACK);
b[centrex][centrey-1].setBackground(Color.BLACK);
b[centrex][centrey-2].setBackground(Color.BLACK);
b[centrex+1][centrey-1].setBackground(Color.BLACK);
b[centrex-1][centrey-1].setBackground(Color.BLACK);
centrex++;
b[centrex+1][centrey].setBackground(Color.WHITE);
b[centrex-1][centrey].setBackground(Color.WHITE);
b[centrex][centrey-1].setBackground(Color.WHITE);
b[centrex][centrey-2].setBackground(Color.WHITE);
b[centrex+1][centrey-1].setBackground(Color.WHITE);
b[centrex-1][centrey-1].setBackground(Color.WHITE);
}
public void left(){
if ((b[centrex-1-1][centrey].getBackground() != Color.BLACK) ||
(b[centrex-1][centrey-2].getBackground() != Color.BLACK) ||
(b[centrex-1-1][centrey-1].getBackground() != Color.BLACK)){
health--;
}
b[centrex+1][centrey].setBackground(Color.BLACK);
b[centrex-1][centrey].setBackground(Color.BLACK);
b[centrex][centrey-1].setBackground(Color.BLACK);
b[centrex][centrey-2].setBackground(Color.BLACK);
b[centrex+1][centrey-1].setBackground(Color.BLACK);
b[centrex-1][centrey-1].setBackground(Color.BLACK);
centrex--;
b[centrex+1][centrey].setBackground(Color.WHITE);
b[centrex-1][centrey].setBackground(Color.WHITE);
b[centrex][centrey-1].setBackground(Color.WHITE);
b[centrex][centrey-2].setBackground(Color.WHITE);
b[centrex+1][centrey-1].setBackground(Color.WHITE);
b[centrex-1][centrey-1].setBackground(Color.WHITE);
}
public void up(){
if ((b[centrex+1][centrey-1-1].getBackground() != Color.BLACK) ||
(b[centrex-1][centrey-1-1].getBackground() != Color.BLACK) ||
(b[centrex][centrey-2-1].getBackground() != Color.BLACK)){
health--;
}
b[centrex+1][centrey].setBackground(Color.BLACK);
b[centrex-1][centrey].setBackground(Color.BLACK);
b[centrex][centrey-1].setBackground(Color.BLACK);
b[centrex][centrey-2].setBackground(Color.BLACK);
b[centrex+1][centrey-1].setBackground(Color.BLACK);
b[centrex-1][centrey-1].setBackground(Color.BLACK);
centrey--;
b[centrex+1][centrey].setBackground(Color.WHITE);
b[centrex-1][centrey].setBackground(Color.WHITE);
b[centrex][centrey-1].setBackground(Color.WHITE);
b[centrex][centrey-2].setBackground(Color.WHITE);
b[centrex+1][centrey-1].setBackground(Color.WHITE);
b[centrex-1][centrey-1].setBackground(Color.WHITE);
}
public void down(){
if ((b[centrex][centrey].getBackground() != Color.BLACK) ||
(b[centrex-1][centrey+1].getBackground() != Color.BLACK) ||
(b[centrex+1][centrey+1].getBackground() != Color.BLACK)){
health--;
}
b[centrex+1][centrey].setBackground(Color.BLACK);
b[centrex-1][centrey].setBackground(Color.BLACK);
b[centrex][centrey-1].setBackground(Color.BLACK);
b[centrex][centrey-2].setBackground(Color.BLACK);
b[centrex+1][centrey-1].setBackground(Color.BLACK);
b[centrex-1][centrey-1].setBackground(Color.BLACK);
centrey++;
b[centrex+1][centrey].setBackground(Color.WHITE);
b[centrex-1][centrey].setBackground(Color.WHITE);
b[centrex][centrey-1].setBackground(Color.WHITE);
b[centrex][centrey-2].setBackground(Color.WHITE);
b[centrex+1][centrey-1].setBackground(Color.WHITE);
b[centrex-1][centrey-1].setBackground(Color.WHITE);
}
public boolean checkmove(int x, int y, int dx, int dy){
if (((x-1+dx) > 0) && ((x+1+dx) < 15) && ((y-2+dy) > -1) && ((y+dy) < 24)){
return true;
} else {
return false;
}
}
@Override
public void keyPressed(KeyEvent e) {
if (e.getKeyCode() == KeyEvent.VK_ENTER){
} else if (e.getKeyCode() == KeyEvent.VK_UP){
if (checkmove(centrex, centrey, 0,-1) == true){
up = true;
}
} else if (e.getKeyCode() ==
KeyEvent.VK_RIGHT){
if (checkmove(centrex, centrey, 1,0) == true){
right = true;
}
} else if (e.getKeyCode() ==
KeyEvent.VK_LEFT){
if (checkmove(centrex, centrey, -1,0) == true){
left = true;
}
} else if (e.getKeyCode() ==
KeyEvent.VK_DOWN){
if (checkmove(centrex, centrey, 0,1) == true){
down = true;
}
}
}
@Override
public void keyReleased(KeyEvent e) {
if (e.getKeyCode() == KeyEvent.VK_SPACE){
fire = true;
}
}
@Override
public void keyTyped(KeyEvent e) {
}
}
import javax.swing.*;
import java.io.*;
import java.awt.*;
class SaveOption extends JFrame
{
FileReader fr=null;
BufferedReader bur=null;
FileWriter fw=null;
BufferedWriter bw=null;
PrintWriter pw=null;
String direct;
void OpenSave() throws Exception
{
JFrame j1=new JFrame();
j1.setVisible(false);
j1.setSize(100,100);
FileDialog fd=new FileDialog(j1,"Choose the savegame file");
fd.setVisible(true);
String file=fd.getDirectory();
String safe=fd.getFile();
//System.out.println(file);
direct=file.replace('\\' ,'/')+safe;
//System.out.println(direct);
/* fr=new FileReader(direct);
bur=new BufferedReader(fr);*/
fw=new FileWriter(direct,true);
bw=new BufferedWriter(fw);
pw=new PrintWriter(bw);
}
void letsSave(int s)
{
String str1 = JOptionPane.showInputDialog("Enter your Name");
pw.println(s+"------------------------------"+str1);
pw.close();
}
}