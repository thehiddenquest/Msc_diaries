public enum Status {
    InCriticalState, // While in critical state
    AfterCriticalState, // just after critical state but can not request right away
    None, // Default
    Requesting // Requesting for critical state
}
