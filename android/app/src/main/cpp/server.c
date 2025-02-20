#include <jni.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/stat.h>

// Function to check if a directory exists
bool directoryExists(const char* path) {
    struct stat st;
    return (stat(path, &st) == 0 && S_ISDIR(st.st_mode));
}

// Function to create a directory if it doesn't exist
void createDir(const char* path) {
    struct stat st;
    if (stat(path, &st) == -1) {
        mkdir(path, 0700);  // Create the directory with read-write-execute permissions for the owner
    }
}

extern "C" JNIEXPORT void JNICALL Java_com_ksweb_LighttpdModule_startHttpServer(JNIEnv *env, jobject thiz) {
        // Ensure that the necessary directories exist
        createDir("/data/data/com.ksweb/files");
        createDir("/data/data/com.ksweb/files/htdocs");
        createDir("/data/data/com.ksweb/files/logs");

        // Directly declare the config as a string
        char *config = "ServerRoot \"/data/data/com.ksweb/files\"\n"
                       "Listen 8080\n"
                       "ServerName localhost\n"
                       "DocumentRoot \"/data/data/com.ksweb/files/htdocs\"\n"
                       "ErrorLog \"/data/data/com.ksweb/files/logs/error_log\"\n"
                       "CustomLog \"/data/data/com.ksweb/files/logs/access_log\" common\n"
                       "DirectoryIndex index.html\n"
                       "<Directory \"/data/data/com.ksweb/files/htdocs\">\n"
                       "    Options Indexes FollowSymLinks\n"
                       "    AllowOverride None\n"
                       "    Require all granted\n"
                       "</Directory>\n";

        // Write the config to a file
        FILE *configFile = fopen("/data/data/com.ksweb/files/httpd.conf", "w");
        if (configFile != NULL) {
            fputs(config, configFile);
            fclose(configFile);

            // Define arguments to pass to liblhttpd (including the config file path)
            char *args[] = {"/data/data/com.ksweb/files/httpd.conf", NULL};

            // Call the main function of liblhttpd.so to start the server
            main(1, args);  // Start the server using the config file
        } else {
            // Handle the error if the file cannot be created or opened
            fprintf(stderr, "Error: Could not create config file\n");
        }
}

