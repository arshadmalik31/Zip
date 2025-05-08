Download openmpi-4.1.4.tar.bz2 from http://www.open-mpi.org in a folder say LP5.
2. Goto the terminal (Command prompt)
3. update using sudo apt-get update
 sudo apt install gcc {if not already installed}
4. Goto the directory which contains the downloaded file
5. Extract the files using tar -jxf openmpi-4.1.4.tar.bz2
 6. The directory openmpi-4.1.4 is created
7. Configure, compile and install by executing the following commands
 ./configure --prefix=$HOME/opt/openmpi
 make all
 make install
8. Now openmpi folder is created in ‘opt‘ folder of Home directory.
9. Now the folder LP5 can be deleted (optional)
10. Update the PATH and LD_LIBRARY_PATH environment variable using
 echo "export PATH=\$PATH:\$HOME/opt/openmpi/bin" >> $HOME/.bashrc
 echo "export 
LD_LIBRARY_PATH=\$LD_LIBRARY_PATH:\$HOME/opt/openmpi/lib">>$HOME/.bashrc
11. Compile the program using
 mpicc name of the program
12. Execute the program using
 mpirun -np N ./a.outHello world program
 nllabc2d22@nllabc2d-22:~/opt/openmpi/bin$ gedit hello.c




#include <stdio.h>
#include "mpi.h"
int main(int argc, char* argv[])
{
int rank, size, len;
MPI_Init(&argc, &argv);
MPI_Comm_rank(MPI_COMM_WORLD, &rank);
MPI_Comm_size(MPI_COMM_WORLD, &size);
printf("Hello, world, I am %d of %d\n",rank, size);
MPI_Finalize();
return 0;
}



mpicc hello.c
mpirun -np 0 ./a.out

mpicc world1.c
mpirun -np 0 ./a.out

mpicc world.c
mpirun -np 0 ./a.out
