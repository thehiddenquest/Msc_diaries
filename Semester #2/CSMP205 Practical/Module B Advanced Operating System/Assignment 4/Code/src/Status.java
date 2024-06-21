/* About status class:
  The Status enum class defines a set of constants representing different states within a program or algorithm.
   PHOLD signifies a node or process in a holding state, often seen in mutual exclusion algorithms where nodes await 
   resource availability or critical section access. REQUESTING indicates active resource request, crucial for managing 
   concurrent access in distributed systems to prevent conflicts. NONE represents a default or undefined state when no specific action is ongoing. 
   ABORT flags abnormal termination conditions, if no node is making a request, or if it is making a request
   to no one for an extended period of time, it may be terminated.,
   enforcing rules governing resource access and ensuring orderly process interactions in distributed environments.
   */

public enum Status {
    PHOLD,      // Node is holding a resource
    REQUESTING, // Node is requesting access to a critical section
    NONE,       // Default or no specific state
    ABORT       // Operation or process is aborted
}
