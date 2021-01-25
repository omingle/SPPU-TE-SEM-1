//============================================================================
// Name        : MyHammingCode.cpp
// Author      : Om Ingle
// Version     :
// Copyright   : Your copyright notice
// Description : Ansi-style
//============================================================================

#include <bits/stdc++.h>

using namespace std;

int binaryToDecimal(int n)
{
    int num = n;
    int dec_value = 0;

    // Initializing base value to 1, i.e 2^0
    int base = 1;

    int temp = num;
    while (temp) {
        int last_digit = temp % 10;
        temp = temp / 10;

        dec_value += last_digit * base;

        base = base * 2;
    }

    return dec_value;
}

string decToBinary(int n) 
{ 
	int i;
	int bin[32];
	for(i=0; n>0; i++)    
	{    
		bin[i]=n%2;    
		n= n/2;  
	}

	string bin_string = "";
	
	for(i=i-1 ;i>=0 ;i--)    
	{    
		bin_string = bin_string + to_string(bin[i]);
	}

	return bin_string;
} 


int main()
{
    int m;
    int r = 0;
    int db[30];

    cout<<"Enter the Number of Data Bits : ";	// 7
    cin>>m;

    int i, j;

    cout<<"\nEnter "<<m<<" Data bits : ";		// 1 1 1 1 0 1 0

    for(i = 0; i < m; i++)
        cin>>db[i];

    while(m + r + 1 >= pow(2, r))				// r = 4
    	r++;

    cout<<"Data Bits are : ";					// 1 1 1 1 0 1 0

    for(i = 0; i < m; i++)
        cout<<db[i]<<" ";

    //Reverse db[]
    int temp[30];

    for (i=m-1,j=0; i>=0; i--,j++)
    {
        temp[j]=db[i];
    }

    for (i=0; i<m; i++)							// 0 1 0 1 1 1 1
    {
        db[i]=temp[i];
    }

    // Redundancy bits
    int nrb = 0;

    //New Data bits after adding redundancy bits
    int l = 0;
    int df[30];

    int k = 1;
    for(i = 1,j = 0; j < m; i++)
    {
        //If it is location of redundancy bit
        if(i==k)
        {
            df[i] = -1;						// -1 -1 0 -1 1 0 1 -1
            k = k * 2;						// 16
            nrb++;							// 4
        }
        else		//If it is location of data bit
        {
            df[i]=db[j];					// -1 -1 0 -1 1 0 1 -1 1 1 1
            j++;
        }
        l++;								// 11
    }

    cout<<"\n\nFrame after Locating positions of redundancy bits : ";

    for(i = l; i > 0; i--)
    {
        cout<<df[i]<<" ";
    }

    for(i = 1; i <= l; i++) {
    	int cnt = 0;
    	if(df[i] == -1) {

    		switch(i) {
    		case 1:
    			cnt = 0;

    			for(j = i + 1; j <= l; j++) {
    				//char res[10];
		    		//itoa(j, res, 2);		// or create user defined function to convert decimal to binary
		    		//string bl(res);
					
					string bl;
					bl = decToBinary(j);
    				char tbit = bl[bl.length()-1];

    				if(tbit == '1') {
    					if(df[j] == 1) {
    						cnt++;
    					}
    				}
    			}

    			break;

    		case 2:
    			cnt = 0;

    			for(j = i + 1; j <= l; j++) {
    				//char res[10];
		    		//itoa(j, res, 2);
		    		//string bl(res);
		    		string bl;
					bl = decToBinary(j);

    				char tbit = bl[bl.length()-2];

    				if(tbit == '1') {
    					if(df[j] == 1) {
    						cnt++;
    					}
    				}
    			}

    			break;

    		case 4:
    			cnt = 0;

    			for(j = i + 1; j <= l; j++) {
    				//char res[10];
		    		//itoa(j, res, 2);
		    		//string bl(res);
		    		string bl;
					bl = decToBinary(j);
    				char tbit = bl[bl.length()-3];

    				if(tbit == '1') {
    					if(df[j] == 1) {
    						cnt++;
    					}
    				}
    			}

    			break;

    		case 8:
    			cnt = 0;

    			for(j = i + 1; j <= l; j++) {
    				//char res[10];
		    		//itoa(j, res, 2);
		    		//string bl(res);
		    		string bl;
					bl = decToBinary(j);
    				char tbit = bl[bl.length()-4];

    				if(tbit == '1') {
    					if(df[j] == 1) {
    						cnt++;
    					}
    				}
    			}

    			break;
    		}

    		if(cnt%2 == 0) {
    			df[i] = 0;
    		}
    		else {
    			df[i] = 1;
    		}

    		cout<<"\nFrame after adding R"<<i<<" = "<<df[i]<<" bit : ";
    		for(i = l; i > 0; i--)
			{
				cout<<df[i]<<" ";
			}
    	}
    }

    cout<<"\n\nFrame after adding redundancy bits : ";

    for(i = l; i > 0; i--)
    {
    	cout<<df[i]<<" ";
    }


    //Get the error bit
	int eb;

	cout<<"\n\nEnter the position of error bit : ";
	cin>>eb;

	cout<<"\nData Bit at position "<<eb<<" is "<<df[eb];

	//Change bit
	if(df[eb]==1)
	{
		df[eb]=0;
	}
	else
	{
		df[eb]=1;
	}

	// Print new data bits after changing the bit
	cout<<"\nData Bits after adding 1-bit error :";
	for(i = l; i > 0; i--)
	{
		cout<<" "<<df[i];
	}

	string rb = "";
    for(i = 1; i <= l; i++) {
    	int cnt = 0;
		switch(i) {
		case 1:
			cnt = 0;

			for(j = i; j <= l; j++) {
				//char res[10];
				//itoa(j, res, 2);		// or create user defined function to convert decimal to binary
				//string bl(res);
		    		string bl;
					bl = decToBinary(j);
				char tbit = bl[bl.length()-1];

				if(tbit == '1') {
					if(df[j] == 1) {
						cnt++;
					}
				}
			}

			if(cnt%2 == 0) {
				rb = rb + "0";
			}
			else {
				rb = rb + "1";
			}

			break;

		case 2:
			cnt = 0;

			for(j = i; j <= l; j++) {
				//char res[10];
				//itoa(j, res, 2);
				//string bl(res);
		    		string bl;
					bl = decToBinary(j);
				char tbit = bl[bl.length()-2];

				if(tbit == '1') {
					if(df[j] == 1) {
						cnt++;
					}
				}
			}

			if(cnt%2 == 0) {
				rb = rb + "0";
			}
			else {
				rb = rb + "1";
			}
			break;

		case 4:
			cnt = 0;

			for(j = i; j <= l; j++) {
				//char res[10];
				//itoa(j, res, 2);
				//string bl(res);
		    		string bl;
					bl = decToBinary(j);
				char tbit = bl[bl.length()-3];

				if(tbit == '1') {
					if(df[j] == 1) {
						cnt++;
					}
				}
			}

			if(cnt%2 == 0) {
				rb = rb + "0";
			}
			else {
				rb = rb + "1";
			}
			break;

		case 8:
			cnt = 0;

			for(j = i; j <= l; j++) {
			//	char res[10];
			//	itoa(j, res, 2);
			//	string bl(res);
		    		string bl;
					bl = decToBinary(j);
				char tbit = bl[bl.length()-4];

				if(tbit == '1') {
					if(df[j] == 1) {
						cnt++;
					}
				}
			}

			if(cnt%2 == 0) {
				rb = rb + "0";
			}
			else {
				rb = rb + "1";
			}
			break;
		}
    }

    //Reverse rb
    reverse(rb.begin(), rb.end());

    int ep = binaryToDecimal(atoi(rb.c_str()));

    cout<<"\n\nPosition of Error bit (Error Detection) : "<<ep;

	if(df[ep]==1)
	{
		df[eb]=0;
	}
	else
	{
		df[eb]=1;
	}

	cout<<"\nData Bits after correcting 1-bit error :";
	for(i = l; i > 0; i--)
	{
		cout<<" "<<df[i];
	}

    return 0;
}
