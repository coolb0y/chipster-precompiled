cmake_minimum_required(VERSION 3.4.1)
project("chipster")

add_library(server SHARED server.c)

find_library(log-lib log)

target_link_libraries(
    server
    ${log-lib}
    # Add other libraries like OpenSSL, pthreads, etc. if required.
)
