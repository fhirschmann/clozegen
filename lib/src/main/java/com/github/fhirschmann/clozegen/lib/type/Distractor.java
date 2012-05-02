

/* First created by JCasGen Wed May 02 22:44:52 CEST 2012 */
package com.github.fhirschmann.clozegen.lib.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Wed May 02 23:03:20 CEST 2012
 * XML source: /home/fabian/dev/clozegen/lib/src/main/resources/desc/type/Distractor.xml
 * @generated */
public class Distractor extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Distractor.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Distractor() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Distractor(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Distractor(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Distractor(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: test

  /** getter for test - gets 
   * @generated */
  public String getTest() {
    if (Distractor_Type.featOkTst && ((Distractor_Type)jcasType).casFeat_test == null)
      jcasType.jcas.throwFeatMissing("test", "com.github.fhirschmann.clozegen.lib.type.Distractor");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Distractor_Type)jcasType).casFeatCode_test);}
    
  /** setter for test - sets  
   * @generated */
  public void setTest(String v) {
    if (Distractor_Type.featOkTst && ((Distractor_Type)jcasType).casFeat_test == null)
      jcasType.jcas.throwFeatMissing("test", "com.github.fhirschmann.clozegen.lib.type.Distractor");
    jcasType.ll_cas.ll_setStringValue(addr, ((Distractor_Type)jcasType).casFeatCode_test, v);}    
  }

    