/*
 * Copyright (c) 2012 Fabian Hirschmann <fabian@hirschmann.email>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */


/* First created by JCasGen Mon May 07 16:36:58 CEST 2012 */
package com.github.fhirschmann.clozegen.lib.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.NonEmptyStringList;
import org.apache.uima.jcas.cas.TOP_Type;
import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Fri May 11 12:25:39 CEST 2012
 * XML source: /home/fabian/dev/clozegen/lib/src/main/resources/desc/type/GapAnnotation.xml
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
  //* Feature: allAnswers

  /** getter for allAnswers - gets 
   * @generated */
  public NonEmptyStringList getAllAnswers() {
    if (GapAnnotation_Type.featOkTst && ((GapAnnotation_Type)jcasType).casFeat_allAnswers == null)
      jcasType.jcas.throwFeatMissing("allAnswers", "com.github.fhirschmann.clozegen.lib.type.GapAnnotation");
    return (NonEmptyStringList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((GapAnnotation_Type)jcasType).casFeatCode_allAnswers)));}
    
  /** setter for allAnswers - sets  
   * @generated */
  public void setAllAnswers(NonEmptyStringList v) {
    if (GapAnnotation_Type.featOkTst && ((GapAnnotation_Type)jcasType).casFeat_allAnswers == null)
      jcasType.jcas.throwFeatMissing("allAnswers", "com.github.fhirschmann.clozegen.lib.type.GapAnnotation");
    jcasType.ll_cas.ll_setRefValue(addr, ((GapAnnotation_Type)jcasType).casFeatCode_allAnswers, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
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

    
