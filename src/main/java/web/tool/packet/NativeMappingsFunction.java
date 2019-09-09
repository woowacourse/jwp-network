package web.tool.packet;

@FunctionalInterface
public interface NativeMappingsFunction<T, R> {
    R apply(T t);
}
