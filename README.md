# CS4341_Assignment4

This is the fourth assignment for CS 4341: Introduction to Artificial Intelligence. For this assignment, we implemented a functional q learning algorithm, where the user provides a tab-delimited text file that represents a 2-dimensional board. An example is provided below,

<!-- 0   0   0   0   0   0   0
0	  1   0	  0	  0	  0	  0
0	  0	  0	  0	  0	  0	  0
0	  0	  0	  0	  0	  0	  0
0	  0	  0	  0	  0	  0	  0
0	  0	  0	  0	  -1	0	  0
0	  0	  0	  0	  0	  0	  0
0	  0	  0	  0	  0	  0	  0 -->

Positive numbers represent good terminal states because it provides positive rewards, while negative numbers represent bad terminal states since they provide negative rewards. This program essentially makes an agent teach itself a policy that it can use to determine about which coordinates are good or bad without any knowledge of the board itself. 


