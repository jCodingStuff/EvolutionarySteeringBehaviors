# EvolutionarySteeringBehaviors

The goal of this project is creating, in Java, a 2D world based on Craig Reynold's paper "Steering Behaviors for Autonomous Characters" and Daniel Shiffman's Coding Challenge 69 Evolutionary Steering Behaviors (LINKS IN THE END OF THE FILE!).

I will try to design a kind of map, in which there will be autonomous agents, food (green circles) and poison (red circles).  
The thing is, the agents have a health number, which will be decreasing as time goes on, so they will have to seek for food (which gives certain points of health).  
This turns interesting when, in the beginning, the attraction and visual field for food and poison for each agent is random (within a range).  
If some agent manages to survive a certain amount of time, it will have the opportunity to clone itself, and "create" a new agent, which will be its child and will share its "knowledge of the world" (and maybe suffer mutation!).  
And yeah, they are cannibals, so when an agent dies, it leaves a piece of food in that certain place, so other agent can eat it!

Finally, I will set general rules: agents are not able to go off the field, food and poison appear at random locations once in a while...
The objective of this project is to simulate a society and see what is the OPTIMAL AGENT.

Craig Reynold's paper: https://www.red3d.com/cwr/steer/  
Dan Shiffman in YouTube: https://www.youtube.com/channel/UCvjgXvBlbQiydffZU7m1_aw  
Coding Challenge 69: https://www.youtube.com/watch?v=flxOkx0yLrY  
