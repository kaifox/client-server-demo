file(GLOB_RECURSE DemoServer_SRCS *.cpp *.h)

set(DemoServer_SRCS
    ${DemoServer_SRCS}
    ${TEMPLATES_SRC}
)

# Create the application
add_library(DemoServer SHARED ${DemoServer_SRCS})

# Link to Cutelyst
target_link_libraries(DemoServer
    Cutelyst::Core
    Cutelyst::StaticSimple
    Qt5::Core
    Qt5::Network
)

