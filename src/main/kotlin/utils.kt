fun loadFromResource(res: String): String {
    return object {}.javaClass.getResource(res).readText()
}
