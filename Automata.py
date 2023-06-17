class Auto:
    def __init__(self):
        self.stack = ["$"]
        self.state = "q0"
        self.op = False
    def transitions(self, symbol):
        if self.state == "q0":
            if symbol.isalpha() or symbol == "_":
                self.state = "q1"
            else:
                return False
        elif self.state == "q1":
            if symbol.isalpha() or symbol.isdigit():
                pass
            elif symbol == "=":
                self.state = "q2"
            else:
                return False
        elif self.state == "q2":
            if symbol == "(":
                self.stack.append("(")
            elif symbol == ")" and len(self.stack) > 1:
                self.stack.pop()
            elif symbol.isdigit() or symbol.isalpha():
                pass
            elif symbol in ['+', '-', '*', '/', '%', '=']:
                self.state = "q3"
            elif symbol == ";":
                self.state = "q4"
            else:
                return False
        elif self.state == "q3":
            if symbol == "(":
                self.stack.append("(")
            elif symbol == ")" and len(self.stack) > 1:
                self.stack.pop()
            elif symbol.isdigit() or symbol.isalpha():
                self.state = "q2"
            elif symbol in ['+', '-', '*', '/', '%', '=']:
                return False
            elif symbol == ";":
                self.state = "q4"
            else:
                return False
        if self.state == "q4":
            if len(self.stack) > 1 or self.stack != ["$"] or symbol != ";":
                return False
        return True
    def IsValid(self, string):
        string = string.replace(" ","")
        for symbol in string:
            if not self.transitions(symbol):
                return False
        if self.state != "q4":
            return False
        return True
strings = [
    "A2 = A1 + 12 + C5;", #si
    "AB = A*B/100-59;", #si
    "ABC = (340 % 2) + (12-C);", #si
    "AC = 10 + 8 * (5+B);", #si
    "Var1 = Var2 = Var3 = 8;", #si
    "VAR = (CatA + ( ( CatA + CatB ) * CatC ))*(CatD - CatF)", #no
    "varisadsfasdfasd =(c5*21)+32/90%12* (catC - catB)", #no
    "3=A2=1+12+C5;", #no
    "AB=A**B/100-59;", #no
    "ABC(340 %", #no
    "POPO=12/2+9*c*a*c*901231321;"
]
for i in strings:
    b = Auto()
    print(b.IsValid(i))