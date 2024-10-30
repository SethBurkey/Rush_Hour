# Rush_Hour
Solves any Rush Hour Game given the same format as the example given in input.txt

Input Format: The first line of the input file specifies the number of cars/trucks N in the
input. Each car/truck requires 5 lines of input to specify; thus the first line will be
followed by N*5 lines. Each group of 5 lines is formatted: 1st line: the string “car” or
“truck”; 2nd line: the color; 3rd line: the orientation, either ‘h’ or ‘v’; 4 th and 5 th : the row
and col coordinate of the vehicle. The r,c coordinate will be the upper end of the vehicle
for vertically oriented vehicles, i.e., the least row coordinate occupied by the vehicle.
The coordinate will be the leftmost end of the vehicle for horizontal vehicles. Thus, the
red car in the sample input occupies (3,2) and (3,3) since it is horizontal and size 2.
Note: in the input the matrix dimensions are numbered 1-6; thus (1,1) is the upper left
corner. Also, the FIRST vehicle in the input must be the “red” car.

Sample Input:
8
car
red
h
3
2
car
lime
h
1
1
truck
purple
v
2
1
car
orange
v
5
1
truck
blue
v
2
4
truck
yellow
v
1
6
car
lightblue
h
5
5
truck
aqua
h
6
3

Sample Output:
8 moves:
lightblue 3 L
yellow 3 D
lime 1 R
purple 1 U
orange 1 U
aqua 2 L
blue 2 D
red 4 R
