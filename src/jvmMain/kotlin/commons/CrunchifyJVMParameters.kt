package commons

object CrunchifyJVMParameters {

    private val mb = 1024 * 1024;
    private val instance = Runtime.getRuntime();

    fun getTotalMemory(): Long {
        return instance.totalMemory() / mb
    }
}
