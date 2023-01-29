# Java Tournament Maker - version 1.1 pool phase

## Overview

The following repo contains a program for generating pools and pool-matches for tournaments.

The program meets the need of allowing for a maximum number of participants while not setting a limit on subscriptions to the tournament. Besides the number of participants the desired and allowed duration of the tournament adds to extra variability.

The program takes as input solely an even number of participants. It turns out often that it is useful to have one entity in reserve anyway. Based on this input the program generates several possible tournament configurations.


Tournament Types

There are two main types of tournament-configurations:

Type 1: all pools are of an even size and of the same size.

Every team can play against any other team in the pool solely. This yields the best intra-pool performance comparison and every pool can do this within the same number of rounds.

There is a special subtype of this kind of tournament, when the number of participants is equal to a power of two. This subtype results not only in an obvious way of creating post-pool-rounds. It also enables an equal number of pool members to continue to the next round. For these reasons the subtype is referred to as a perfect tournament.

The tournaments of type 1 where the number of participants is not equal to a power of 2 are required to compare inter-pool performance. This results in delegating an uneven number of pool-members to the next round and mildly contaminating it, while it will also be unknown how many members a pool will delegate to ‘the playoffs’ eventually.

While tournament type 1 usually generates a usable option, one might have to resort to a tournament of type 2: tournaments that besides even pools (possibly zero even pools), have two pools of uneven size. 

Pools of uneven size are undesirable, because it leads to both inter-pool and intra-pool impurity. The main issue is that one team is not able to be teamed up inside the pool. To enable the unpaired team to also play it can be paired to an unpaired team in the other pool of uneven size.

Inter-pool performance comparison is also contaminated by having pools of uneven size, but having this type of tournament can still be the best option sometimes.



Tournament Creation

After selection of an option an appropriate tournament is created with the corresponding number of pools of the required type. 
It also creates the matches belonging to each pool-round. The tournament pool-phase is presented by the presentation of each pool by a unique ID and each pool round and its corresponding matches by a Match ID. (A match between teams of two sister-pools currently uses a different match-id: A.B.1 and B.A.1 both refer to the match between team 1 of pool A and team 2 of pool A.)
