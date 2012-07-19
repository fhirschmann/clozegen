Architecture
============

The following picture shows the architectural overview
of ClozeGen:

![Architecutre](images/arch.png "Architectural Overview")

Steps in the process:

1. Read an input `text` (from plain text, pdf)
2. Translate `text` into the internal representation ([UIMA](http://uima.apache.org))
2. Activate one or more Gap Annotators
3. Translate the internal representation into the Intermediate Format (let the user adjust the result)
4. Reverse the last step
5. Export to plain-text or LaTeX

When developing new gap generators, you'll only have to worry
about the *Gap Annotation* step, which is
[explained in the tutorial](./tutorial.html).
