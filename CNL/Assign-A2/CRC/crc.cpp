//Assignment 2: CRC
//Name: 
//Batch: L3
//Roll No:

#include <iostream>
using namespace std;

int main(){

    int dataSize;
 
    cout<<"Enter Size of data(Dataword): ";
    cin>>dataSize; 										// 6
    int a[20];
 
    cout<<"Enter data:\n";
    for(int i=0;i<dataSize;i++) {
        cin>>a[i];										// a = 1 0 0 1 0 0
    }
    
    int divisorSize;
    cout<<"\nEnter size of the divisor : ";
    cin>>divisorSize; 									// 4
    int b[20];
   
    cout<<"Enter divisor:\n";
    for(int i=0;i<divisorSize;i++) {
        cin>>b[i];										// b = 1 1 0 1
    }
    
    cout<<"\nSender-Side Data ";
    for(int i=0;i<dataSize;i++) {
        cout<<a[i];										// a = 1 0 0 1 0 0
    }
    
    cout<<"\nDivisor :";
    for(int i=0;i<divisorSize;i++) {
        cout<<b[i];										// b = 1 1 0 1
    }
    
    int x=divisorSize-1;								// x = 3
    for (int i=dataSize;i<dataSize+x;i++) {
        a[i]=0;											// a = 1 0 0 1 0 0 0 0 0
    }
    
    int temp[20];
    for(int i=0;i<20;i++) {
        temp[i]=a[i];									// temp = 1 0 0 1 0 0 0 0 0
    }
    
    for(int i=0;i<dataSize;i++) {
        int j=0,k=i;
        if (temp[k]>=b[j]) {
            for(int j=0,k=i;j<divisorSize;j++,k++) {
                if((temp[k]==1 && b[j]==1) || (temp[k]==0 && b[j]==0)) {
                    temp[k]=0;
                }
                else {
                    temp[k]=1;
                }
            }
        }
    }
    
    int crc[15];
    for(int i=0,j=dataSize;i<x;i++,j++) {
        crc[i]=temp[j];
    }
    
    cout<<"\nCRC bits: ";
    for(int i=0;i<x;i++) {
        cout<<crc[i];
    }
    
    cout<<"\n\nData that should be transmitted: ";
    int tf[15];
    for(int i=0;i<dataSize;i++) {
        tf[i]=a[i];
    }
    
    for(int i=dataSize,j=0;i<dataSize+x;i++,j++) {
        tf[i]=crc[j];
    }
    
    for(int i=0;i<dataSize+x;i++) {
        cout<<tf[i];
    }
    cout<<"\n";
    
    int ch;
    cout<<"\nEnter 1 to transmit correct data\nEnter 0 to transmit false data ";
    cin>>ch;
    
    if(ch==0){
        int p;
        cout<<"\nEnter position at which data should be change from 0 position to "<<dataSize+x<<" position \n";
        cin>>p;
        if(p<=dataSize+x)
            tf[p-1] = !tf[p-1];
    }
    
    cout<<"\nReceiver-side : ";
    cout<<"\nReceived Data : ";
    for(int i=0;i<dataSize+x;i++) {
        cout<<tf[i];
    }
    
    for(int i=0;i<dataSize+x;i++) {
        temp[i]=tf[i];
    }
    
    for(int i=0;i<dataSize;i++) {
        int j=0,k=i;
        if (temp[k]>=b[j]) {
            for(int j=0,k=i;j<divisorSize;j++,k++) {
                if((temp[k]==1 && b[j]==1) || (temp[k]==0 && b[j]==0)) {
                    temp[k]=0;
                }
                else {
                    temp[k]=1;
                }
            }
        }
    }
    
    cout<<"\n";
    cout<<"\nRemainder: ";
    int rem[15];
    for (int i=dataSize,j=0;j<x;i++,j++) {
        rem[j]= temp[i];
        cout<<rem[j];
    }
    
    int flag=0;
    for(int i=0;i<x;i++) {
        if(rem[i]!=0) {
            flag=1;
        }
    }
    
    if(flag==0) {
        cout<<"\nRemainder Is 0, threfore message transmitted from sender to receiver is correct\n";
    }
    else {
        cout<<"\nRemainder Is Not 0(Syndrome), therefore message transmitted from sender to receiver contains error\n";
    }
    
    
	return 0;
}
