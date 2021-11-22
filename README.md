# Evolutionary Steering Behaviors

<img src="/imgs/snapshot.png" alt="Thumbnail" width="65%"/>

The goal of this project is to create, in Java, a 2D world based on Craig Reynold's paper "[Steering Behaviors for Autonomous Characters](https://www.red3d.com/cwr/steer/)" and [Daniel Shiffman](https://www.youtube.com/channel/UCvjgXvBlbQiydffZU7m1_aw)'s [Coding Challenge 69 Evolutionary Steering Behaviors](https://www.youtube.com/watch?v=flxOkx0yLrY).

I will try to design a kind of map, in which there will be autonomous agents, food (green circles) and poison (red circles).  
The thing is, the agents have a health number, which will be decreasing as time goes on, so they will have to seek for food (which gives certain points of health).  
This turns interesting when, in the beginning, the attraction and visual field for food and poison for each agent is random (within a range).  
If some agent manages to survive a certain amount of time, it will have the opportunity to clone itself, and "create" a new agent, which will be its child and will share its "knowledge of the world" (and maybe suffer mutation!).  
And yeah, they are cannibals, so when an agent dies, it leaves a piece of food in that certain place, so other agent can eat it!

Finally, I will set general rules: agents are not able to go off the field, food and poison appear at random locations once in a while...
The objective of this project is to simulate a society and see what is the OPTIMAL AGENT.
