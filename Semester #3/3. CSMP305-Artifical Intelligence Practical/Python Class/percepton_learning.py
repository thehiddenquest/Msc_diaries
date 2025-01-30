import numpy as np

w = np.array([0,0,0,0])
eta = int(1)
theta = int(0)

inputs = np.array([[1,1,1,1],
         [1,-1,1,1],
         [1,1,-1,1],
         [1,1,1,-1]])

target = [1,-1,-1,-1]

print("Inputs", inputs)
print("Target", target)
print("Weight", w)
flag = True
epoch = int(1)
while(flag):
    print("Epoch",epoch)
    epoch +=1
    flag = False
    for i in range(inputs.shape[0]):
        print("For input ",i)
        net_input = sum(inputs[i,:]*w)
        print("net_input",net_input)
        if(net_input > theta):
            y_out = 1
        elif(net_input>= -theta and net_input <= theta):
            y_out = 0
        else:
            y_out = -1
        print("Y_out", y_out)
        if(y_out != target[i]):
            flag = True
            w[:]+=eta*target[i]*inputs[i,:]
            print("Updated w", w)

    
    