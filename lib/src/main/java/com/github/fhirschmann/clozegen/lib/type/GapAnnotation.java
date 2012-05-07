

/* First created by JCasGen Mon May 07 16:36:58 CEST 2012 */
package com.github.fhirschmann.clozegen.lib.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.NonEmptyStringList;
import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Mon May 07 16:36:58 CEST 2012
 * XML source: /home/fabian/dev/clozegen/lib/src/main/resources/desc/type/Distractor.xml
 * @generated */
public class GapAnnotation extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(GapAnnotation.class);
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
  protected GapAnnotation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public GapAnnotation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public GapAnnotation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public GapAnnotation(JCas jcas, int begin, int end) {
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
  //* Feature: invalidAnswers

  /** getter for invalidAnswers - gets 
   * @generated */
  public NonEmptyStringList getInvalidAnswers() {
    if (GapAnnotation_Type.featOkTst && ((GapAnnotation_Type)jcasType).casFeat_invalidAnswers == null)
      jcasType.jcas.throwFeatMissing("invalidAnswers", "com.github.fhirschmann.clozegen.lib.type.GapAnnotation");
    return (NonEmptyStringList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((GapAnnotation_Type)jcasType).casFeatCode_invalidAnswers)));}
    
  /** setter for invalidAnswers - sets  
   * @generated */
  public void setInvalidAnswers(NonEmptyStringList v) {
    if (GapAnnotation_Type.featOkTst && ((GapAnnotation_Type)jcasType).casFeat_invalidAnswers == null)
      jcasType.jcas.throwFeatMissing("invalidAnswers", "com.github.fhirschmann.clozegen.lib.type.GapAnnotation");
    jcasType.ll_cas.ll_setRefValue(addr, ((GapAnnotation_Type)jcasType).casFeatCode_invalidAnswers, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: validAnswers

  /** getter for validAnswers - gets 
   * @generated */
  public NonEmptyStringList getValidAnswers() {
    if (GapAnnotation_Type.featOkTst && ((GapAnnotation_Type)jcasType).casFeat_validAnswers == null)
      jcasType.jcas.throwFeatMissing("validAnswers", "com.github.fhirschmann.clozegen.lib.type.GapAnnotation");
    return (NonEmptyStringList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((GapAnnotation_Type)jcasType).casFeatCode_validAnswers)));}
    
  /** setter for validAnswers - sets  
   * @generated */
  public void setValidAnswers(NonEmptyStringList v) {
    if (GapAnnotation_Type.featOkTst && ((GapAnnotation_Type)jcasType).casFeat_validAnswers == null)
      jcasType.jcas.throwFeatMissing("validAnswers", "com.github.fhirschmann.clozegen.lib.type.GapAnnotation");
    jcasType.ll_cas.ll_setRefValue(addr, ((GapAnnotation_Type)jcasType).casFeatCode_validAnswers, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    