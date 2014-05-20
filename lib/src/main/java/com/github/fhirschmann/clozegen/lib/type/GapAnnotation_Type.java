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

import org.apache.uima.cas.Feature;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Fri May 11 12:25:39 CEST 2012
 * @generated */
public class GapAnnotation_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (GapAnnotation_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = GapAnnotation_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new GapAnnotation(addr, GapAnnotation_Type.this);
  			   GapAnnotation_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new GapAnnotation(addr, GapAnnotation_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = GapAnnotation.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("com.github.fhirschmann.clozegen.lib.type.GapAnnotation");
 
  /** @generated */
  final Feature casFeat_allAnswers;
  /** @generated */
  final int     casFeatCode_allAnswers;
  /** @generated */ 
  public int getAllAnswers(int addr) {
        if (featOkTst && casFeat_allAnswers == null)
      jcas.throwFeatMissing("allAnswers", "com.github.fhirschmann.clozegen.lib.type.GapAnnotation");
    return ll_cas.ll_getRefValue(addr, casFeatCode_allAnswers);
  }
  /** @generated */    
  public void setAllAnswers(int addr, int v) {
        if (featOkTst && casFeat_allAnswers == null)
      jcas.throwFeatMissing("allAnswers", "com.github.fhirschmann.clozegen.lib.type.GapAnnotation");
    ll_cas.ll_setRefValue(addr, casFeatCode_allAnswers, v);}
    
  
 
  /** @generated */
  final Feature casFeat_validAnswers;
  /** @generated */
  final int     casFeatCode_validAnswers;
  /** @generated */ 
  public int getValidAnswers(int addr) {
        if (featOkTst && casFeat_validAnswers == null)
      jcas.throwFeatMissing("validAnswers", "com.github.fhirschmann.clozegen.lib.type.GapAnnotation");
    return ll_cas.ll_getRefValue(addr, casFeatCode_validAnswers);
  }
  /** @generated */    
  public void setValidAnswers(int addr, int v) {
        if (featOkTst && casFeat_validAnswers == null)
      jcas.throwFeatMissing("validAnswers", "com.github.fhirschmann.clozegen.lib.type.GapAnnotation");
    ll_cas.ll_setRefValue(addr, casFeatCode_validAnswers, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public GapAnnotation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_allAnswers = jcas.getRequiredFeatureDE(casType, "allAnswers", "uima.cas.NonEmptyStringList", featOkTst);
    casFeatCode_allAnswers  = (null == casFeat_allAnswers) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_allAnswers).getCode();

 
    casFeat_validAnswers = jcas.getRequiredFeatureDE(casType, "validAnswers", "uima.cas.NonEmptyStringList", featOkTst);
    casFeatCode_validAnswers  = (null == casFeat_validAnswers) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_validAnswers).getCode();

  }
}



    
