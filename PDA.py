class PDA:
    def __init__(self):
        self.stack = ['$']  # Pila inicial con un símbolo de fondo ('$')
        self.state = 'q0'  # Estado inicial
        self.operation = False
    def transition(self, symbol):
        print(self.state)
        if symbol == ' ':
            return True  # Ignorar espacios, transición válida
        if self.state == 'q0':
            if symbol.isalpha():
                self.state = 'q1'  # Cambia al estado q1
            elif symbol.isdigit():
                self.state = 'q2'  # Cambia al estado q2
            elif symbol in ['+', '-', '*', '/', '%']:
                self.state = 'q3'  # Cambia al estado q3
            elif symbol == '(':
                self.stack.append('(')  # Empuja '(' en la pila
            else:
                return False  # Caracter no válido, cadena no válida
        elif self.state == 'q1':
            if symbol.isalnum() or symbol == '_' or symbol.isalpha():
                pass  # Continúa en el estado q1
            elif symbol == '=':
                self.state = 'q4'  # Cambia al estado q4 para leer el siguiente término
            else:
                return False  # Caracter no válido, cadena no válida
        elif self.state == 'q2':
            if symbol.isdigit() or symbol.isalpha():
                pass  # Continúa en el estado q2
            elif symbol == '=':
                self.state = 'q4'  # Cambia al estado q4 para leer el siguiente término
            else:
                return False  # Caracter no válido, cadena no válida
        elif self.state == 'q3':
            if symbol == '(':
                self.stack.append('(')  # Empuja '(' en la pila
            else:
                return False  # Caracter no válido, cadena no válida
        elif self.state == 'q4':
            if(symbol == '('):
                self.stack.append('(')
            elif(symbol == ')' and len(self.stack)>1):
                self.stack.pop()
            elif symbol == ')' and len(self.stack)== 1:
                return False
        elif self.state == 'q4':
            if symbol.isalnum() or symbol == '_' or symbol.isalpha() or symbol in ['+', '-', '*', '/', '%']:
                pass  # Continúa en el estado q4
            elif symbol == ';':
                self.state = 'q5'  # Cambia al estado q5 para terminar la expresión
            else:
                return False  # Caracter no válido, cadena no válida
        if self.state == 'q5':
            if symbol == ';':
                print(self.stack)
                if len(self.stack) == 2:
                    self.stack.pop()
                if self.stack == ['$']:  # Si la pila está vacía
                    self.state = 'q6'  # Cambia al estado de aceptación q6
            else:
                return False  # Caracter no válido, cadena no válida

        return True  # Caracter válido
    def accept(self):
        return self.state == 'q6'
def pda_recognize(input_string):
    pda = PDA()
    print(input_string)
    for symbol in input_string:
        if not pda.transition(symbol):
            return False
    return pda.accept()
# Ejemplos de uso
valid_strings = [
    "A2 = A1 + 12 + C5;",
    "AB = A*B/100-59;",
    "ABC = (340 % 2) + (12-C);",
    "AC = 10 + 8 * (5+B);",
    "Var1 = Var2 = Var3 = 8;",
    "VAR = (CatA + ( ( CatA + CatB ) * CatC ))*(CatD - CatF)",
    "varisadsfasdfasd =(c5*21)+32/90%12* (catC - catB)"
]
invalid_strings = [
    "3=A2=1+12+C5;",
    "AB=A**B/100-59;",
    "ABC(340 %"
]
for string in valid_strings:
    print(string)
    if pda_recognize(string):
        print(f"La cadena '{string}' es válida.")
    else:
        print(f"La cadena '{string}' no es válida.")
    input()
for string in invalid_strings:
    if not pda_recognize(string):
        print(f"La cadena '{string}' no es válida.")
    else:
        print(f"La cadena '{string}' es válida.")
