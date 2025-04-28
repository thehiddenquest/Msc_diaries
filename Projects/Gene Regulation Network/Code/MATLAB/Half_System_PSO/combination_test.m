clc
n=8;
reg = 4;
combination = 1:n;
pop = nchoosek(combination,reg);
pop2 = nchoosek(combination, reg-1);
if size(pop, 2) > size(pop2, 2)
    pop2 = [pop2, zeros(size(pop2, 1), size(pop, 2) - size(pop2, 2))];
end
pop = [pop; pop2];
pop3 = nchoosek(combination, reg-2);
if size(pop, 2) > size(pop3, 2)
    pop3 = [pop3, zeros(size(pop3, 1), size(pop, 2) - size(pop3, 2))];
end
pop = [pop; pop3];
pop4 = nchoosek(combination, reg-3);
if size(pop, 2) > size(pop4, 2)
    pop4 = [pop4, zeros(size(pop4, 1), size(pop, 2) - size(pop4, 2))];
end
pop = [pop; pop4];
[pop_size, ~] = size(pop);
disp(['Pop size: ',num2str(pop_size)]);
disp(pop);
for i = 1:pop_size
    for k =1:4
        if pop(i,k) > 0
            fprintf('%d ', pop(i,k));
        end
        fprintf('\n');
    end
end