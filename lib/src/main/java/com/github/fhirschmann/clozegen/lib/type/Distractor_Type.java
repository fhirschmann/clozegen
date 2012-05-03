
/* First created by JCasGen Wed May 02 22:44:52 CEST 2012 */
package com.github.fhirschmann.clozegen.lib.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Thu May 03 20:13:34 CEST 2012
 * @generated */
public class Distractor_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Distractor_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Distractor_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Distractor(addr, Distractor_Type.this);
  			   Distractor_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Distractor(addr, Distractor_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Distractor.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("com.github.fhirschmann.clozegen.lib.type.Distractor");
 
  /** @generated */
  final Feature casFeat_distractors;
  /** @generated */
  final int     casFeatCode_distractors;
  /** @generated */ 
  public int getDistractors(int addr) {
        if (featOkTst && casFeat_distractors == null)
      jcas.throwFeatMissing("distractors", "com.github.fhirschmann.clozegen.lib.type.Distractor");
    return ll_cas.ll_getRefValue(addr, casFeatCode_distractors);
  }
  /** @generated */    
  public void setDistractors(int addr, int v) {
        if (featOkTst && casFeat_distractors == null)
      jcas.throwFeatMissing("distractors", "com.github.fhirschmann.clozegen.lib.type.Distractor");
    ll_cas.ll_setRefValue(addr, casFeatCode_distractors, v);}
    
  
 
  /** @generated */
  final Feature casFeat_acceptables;
  /** @generated */
  final int     casFeatCode_acceptables;
  /** @generated */ 
  public int getAcceptables(int addr) {
        if (featOkTst && casFeat_acceptables == null)
      jcas.throwFeatMissing("acceptables", "com.github.fhirschmann.clozegen.lib.type.Distractor");
    return ll_cas.ll_getRefValue(addr, casFeatCode_acceptables);
  }
  /** @generated */    
  public void setAcceptables(int addr, int v) {
        if (featOkTst && casFeat_acceptables == null)
      jcas.throwFeatMissing("acceptables", "com.github.fhirschmann.clozegen.lib.type.Distractor");
    ll_cas.ll_setRefValue(addr, casFeatCode_acceptables, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Distractor_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_distractors = jcas.getRequiredFeatureDE(casType, "distractors", "uima.cas.NonEmptyStringList", featOkTst);
    casFeatCode_distractors  = (null == casFeat_distractors) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_distractors).getCode();

 
    casFeat_acceptables = jcas.getRequiredFeatureDE(casType, "acceptables", "uima.cas.NonEmptyStringList", featOkTst);
    casFeatCode_acceptables  = (null == casFeat_acceptables) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_acceptables).getCode();

  }
}



    