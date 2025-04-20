function[fitness,next_tp] =  HS(gn_st,dt,gn,tp,pop,reg,X,pop_size)

for i = 1:pop_size    
    w(1,:) = X(gn,1:reg,i);
    alpha = X(gn,reg+1,i);
    pi = X(gn,reg+2,i);    
    for j = 1:tp
        var1 = 1;        
        for k = 1:reg
            if(gn_st(j,pop(i,k))>0)
            var1 = var1*(gn_st(j,pop(i,k))^(w(1,k)));
            end
        end
        next_tp(j,gn,i) = dt*alpha*var1 + (1 - dt*pi)*gn_st(j,gn);
    end
    
    %%Error calculation
    
    diff = 0;
    
    for j = 1:tp
        diff = diff + (next_tp(j,gn,i) - gn_st(j+1,gn))^2;
    end
    
    fitness(1,i) = diff/tp;
end


end