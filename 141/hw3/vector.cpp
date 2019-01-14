/*
 * @Vu Q.Tran, 48894667, HW3@CS141
 */
#include <iostream>
#include <initializer_list>
#include <exception>

using namespace std;

template <typename T>
class Vector {
   template<typename Q> //why this works?
   friend Vector<Q> operator*(const int n, const Vector<Q> &otherVector); //V1 = 20*V2
   template<typename Q>
   friend Vector<Q> operator+(const int n, const Vector<Q> &otherVector);
   template<typename Q>
   friend ostream& operator<<(ostream& out, const Vector<Q> &otherVector); //cout<<V2

   public:
     Vector(int n);
     Vector(initializer_list<T> L);
     ~Vector();
     Vector(const Vector &otherVector);
     int size() const;
     T& operator[](const int i); // T x = Vector[i]
     T operator*(const Vector &otherVector) const; // T x = V1 * V2
     Vector operator+(const Vector &otherVector) const; // Tx = V1 + V2
     const Vector& operator= (const Vector &otherVector); //V1 = V2
     bool operator==(const Vector &otherVector) const; // if (V1==V2)
     bool operator!=(const Vector &otherVector) const; //if (V1 != V2)

   private:
     int sz;
     T *buf;
};

template<typename T> //remember this for making definitions
Vector<T>::Vector (int n) {
    this->sz = n;
    this->buf = new T[sz];
}

template<typename T>
Vector<T>::Vector(initializer_list<T> L) {
    this->sz = L.size();
    this->buf = new T[sz];
    const T* iter = L.begin(); //https://en.cppreference.com/w/cpp/utility/initializer_list
    //initializer_list<T>::iter iter; //why this error?
    int i = 0;
    for (iter = L.begin(); iter != L.end(); iter++) {
        this->buf[i] = (*iter);
        i++;
    }
}

template<typename T>
Vector<T>::~Vector() {
    delete [] this->buf;
    this->buf = nullptr;
}

template<typename T>
Vector<T>::Vector(const Vector &otherVector) {
    this->sz = otherVector.size();
    this->buf = new T[sz];
    for (int i = 0; i < sz; i++) {
        this->buf[i] = otherVector.buf[i];
    }
}

template<typename T>
int Vector<T>::size() const {
    return this->sz;
}

template<typename T>
T& Vector<T>::operator[] (const int i) {

    if (i >=0 && i < this->sz)
        return this->buf[i]; //return as reference so that assignment works
    else {
        exception e;       
        cerr << "Out of bound error "; 
        throw e ; //Out of bound access error";
        return this->buf[0]; //out of bound error, temporary return, represented for T& null?
    }
}

template<typename T>
T Vector<T>::operator*(const Vector<T> &otherVector) const{
    int shorterSize  = 0;
    shorterSize = (this->sz < otherVector.sz)? this->sz : otherVector.sz;
    T result = 0; //if you dont know T in advance, how to initialize it?
    for (int i=0; i < shorterSize; i++){
        result += this->buf[i] * otherVector.buf[i];
    }
    return result;
}

template<typename T>
Vector<T> Vector<T>::operator+(const Vector<T> &otherVector) const {
    Vector<T> sumVect(*this); //copy constructor
    int shorterSize = otherVector.sz; //assume other is shorter
    if (this->sz < otherVector.sz) {
        sumVect = otherVector; //assignment operator
        shorterSize = this->sz;
    }

    for (int i=0; i < shorterSize; i++) { //prevent out of bound access
        sumVect.buf[i] = this->buf[i] + otherVector.buf[i];
    }
    return sumVect;
}

template<typename T>
const Vector<T>& Vector<T>::operator=(const Vector<T> &otherVector){
    this->~Vector(); //free its current memory before get the copy
    //this->Vector(otherVector); //why this not allowed?
    this->sz = otherVector.sz;
    this->buf = new T[sz];
    for (int i = 0; i < sz; i++) {
        this->buf[i] = otherVector.buf[i];
    }
    return (*this); //pointer pointing to reference (mem address)
}

template<typename T>
bool Vector<T>::operator==(const Vector<T>& otherVector) const {
    if (this->sz != otherVector.sz)
        return false;
    for (int i=0; i<sz; i++) {
        if (this->buf[i] != otherVector.buf[i])
            return false;
    }
    return true;
}

template<typename T>
bool Vector<T>::operator!=(const Vector<T>& otherVector) const {
    return !(this->operator==(otherVector));
}

template<typename T>
Vector<T> operator*(const int n, const Vector<T> &otherVector) { //friend func, not a member function
    Vector<T> tmp(otherVector);
    for (int i=0; i< tmp.sz; i++){
        tmp.buf[i]= n * otherVector.buf[i];
    }
    return tmp; // V2 = n * V1 but (n*V1) = tmp, similar explanation below.
}

template<typename T>
Vector<T> operator+(const int n, const Vector<T> &otherVector) { //friend func, not a member function
    Vector<T> tmp(otherVector);
    for (int i=0; i< tmp.sz; i++){
        tmp.buf[i]= n + otherVector.buf[i];
    }
    return tmp; // V2 = n + V1 but (n+V1) = return tmp, compiler will mark (n+V1) as temporary obj at outer scope,
                // then it call the assignment operator= ,
                // after finished, (n+V1) temporary obj will call destructor (debug to see how it really works)
}

template<typename T>
ostream& operator<<(ostream & out, const Vector<T> &otherVector){
    out <<"(";
    for (int i=0; i < otherVector.size(); i++) {
        out << otherVector.buf[i];
        if (i!= (otherVector.size() - 1)) //not print , for the last
            out << ",";
    }
    out << ")";
    return out;
} //cout<<V2


template<typename T>
void test(Vector<T> &v1, Vector<T> &v4) {
    cout << "\nStarts test case with v1=: " << v1 << endl;
    cout << "Test contructor v[1]=" << v1 << endl;
    cout << "Test member func size(): " << v1.size() << endl;
    cout << "Test access subscript v1[3]: " << v1[3] << endl;
    cout << "Test access out of bound v1[-1]: " ;
    try {
      v1[-1];
    } 
    catch (const exception& e) {cout << e.what() << endl;}
    Vector<T> v2(v1.size());
    v2 = 2*v1;
    cout << "Test v2 = 2 * v[1] = " << 2*v1 <<endl;
    cout << "Test x = v1 * v2 = " << v1 << " * " << v2 << "= " << v1 * v2 << endl;
    cout << "Test v3 = v1 + v2 = " << v1 << " + " << v2 << "= " << v1 + v2 << endl;
    cout << "Test v1" << v1 << " + v4" << v4 << " = " << v1 + v4 << endl;
    v1 = v2;
    cout << "Test assignment: v1 = v2, print v1 = " << v1 << endl;
    cout << "Test logic comparison v1 == v2: " << (v1==v2) << endl;
    cout << "Test logic comparison v1 != v2: " << (v1!=v2) << endl;
    v1 = 20 * v2;
    cout << "Test v1 = 20 * v2: " << v1 << endl;
    v1 = 20 + v2;
    cout << "Test v1 = 20 + v2: " << v1 << endl;
    cout << "Test cout << v2: " << v2 << endl;
    cout << "End test case." << endl;
}
int main() {
    cout << "Professor's test case" <<endl;
    Vector<int> intVec{1,3,5,7,9};
    Vector<double> doubleVec{1.5,2.5,3.5,4.5};
    Vector<int> iv(intVec);
    Vector<double> dv(doubleVec);
    cout << "intVect" << intVec << endl;
    cout << "intVec"<< intVec << endl;
    cout << "iv" << iv << endl; // “iv(1, 3, 5, 7, 9)”
    cout << "doubleVec" << doubleVec << endl; // “doubleVec(1.5, 2.5, 3.5, 4.5)”
    cout << "dv" << dv << endl; // “dv(1.5, 2.5, 3.5, 4.5)”
    // add at least one test case for each method defined in Vector

    //add different size vector for my test cases: int & double
    Vector<int> v4{1,2,3};
    Vector<double> v5{1.5,2.5,3.5};
    test(intVec, v4);
    test(doubleVec, v5);
    return 0;
}



















