
/* First created by JCasGen Mon May 07 16:36:58 CEST 2012 */
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
 * Updated by JCasGen Mon May 07 16:36:58 CEST 2012
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
  final Feature casFeat_invalidAnswers;
  /** @generated */
  final int     casFeatCode_invalidAnswers;
  /** @generated */ 
  public int getInvalidAnswers(int addr) {
        if (featOkTst && casFeat_invalidAnswers == null)
      jcas.throwFeatMissing("invalidAnswers", "com.github.fhirschmann.clozegen.lib.type.GapAnnotation");
    return ll_cas.ll_getRefValue(addr, casFeatCode_invalidAnswers);
  }
  /** @generated */    
  public void setInvalidAnswers(int addr, int v) {
        if (featOkTst && casFeat_invalidAnswers == null)
      jcas.throwFeatMissing("invalidAnswers", "com.github.fhirschmann.clozegen.lib.type.GapAnnotation");
    ll_cas.ll_setRefValue(addr, casFeatCode_invalidAnswers, v);}
    
  
 
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

 
    casFeat_invalidAnswers = jcas.getRequiredFeatureDE(casType, "invalidAnswers", "uima.cas.NonEmptyStringList", featOkTst);
    casFeatCode_invalidAnswers  = (null == casFeat_invalidAnswers) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_invalidAnswers).getCode();

 
    casFeat_validAnswers = jcas.getRequiredFeatureDE(casType, "validAnswers", "uima.cas.NonEmptyStringList", featOkTst);
    casFeatCode_validAnswers  = (null == casFeat_validAnswers) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_validAnswers).getCode();

  }
}



    