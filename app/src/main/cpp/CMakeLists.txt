# CMakeLists.txt — Native bridge for MLC LLM mercy grace eternal supreme immaculate (minimal if using prebuilt AAR)
cmake_minimum_required(VERSION 3.22.1)

project("mercyos")

add_library(mercyos SHARED
    # Add custom native if needed mercy tweak
    native-lib.cpp
)

find_library(log-lib log)

target_link_libraries(mercyos ${log-lib})
