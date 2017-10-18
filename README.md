# EvolutionarySteeringBehaviours

The goal of this project is creating, in Java, a 2D world based on Craig Reynold's paper "Steering Behaviors for Autonomous Characters" and Daniel Shiffman's Coding Challenge 69 Evolutionary Steering Behaviors (LINKS IN THE END OF THE FILE!).

I will try to design a kind of map, in which there will be autonomous agents (triangles), food (green circles) and poison (red circles).
The thing is, the agents have a health, which will be decreasing as time goes on, so they will have to seek for food (which gives certain points of health).
This turns interesting when, in the beginning, the food and the poison mean the same to the agents. They will have to eat poison to know that red circles are not beneficial for them (and maybe tell the others, who knows?).
If some agent manages to survive a certain amount of time, it will have the opportunity to join another survivor agent, and "create" a new agent, which will be their child and will share their "knowledge of the world" (and maybe suffer mutation!).
As people, after many time, some agents will be "old", so their speed, visual radius, etc. will decrease and in the end they will die.
And yeah, they are cannibals, so when an agent dies, it leaves a piece of food in that certain place, so other agent can eat it!

Finally, I will set general rules: agents are not able to go off the field, food and poison appear at random locations once in a while...
The objective of this project is to simulate a society and see what is the OPTIMAL AGENT.

16-10-2017: creating a custom vector class to work with the physics of the world (steering forces).
18-10-2017: creating the window, the canvas, and the framerate.
18-10-2017: creating the agents, the food, and the poison
18-10-2017 (CURRENT STATUS): creating the steering forces and evolving stuff

Craig Reynold's paper: https://www.red3d.com/cwr/steer/
Dan Shiffman in YouTube: https://www.youtube.com/channel/UCvjgXvBlbQiydffZU7m1_aw
Coding Challenge 69: https://www.youtube.com/watch?v=flxOkx0yLrY
