package com.harry.common;

public class ViewParamter {
    private String name;
    private String from;

    public ViewParamter(String name, String from) {
        this.name = name;
        this.from = from;
    }

    public String getName() {
        return name;
    }

    public String getFrom() {
        return from;
    }
    /*
     * vm volumes on a per-node basis are stored in local image containers in which are ‘stacks’ of disk images. when a
     * snapshot is taken, a new disk image is created on top of the existing stack where ‘live’ updates are made while
     * the stack of disk images below the live disk image are frozen in time.
     * 
     * the idea with creating a vm from a snapshot is to use a pointer into this stack of disk images for each volume
     * that will form the basis of the volumes in the new vm. a new ‘live’ disk image would then be created on top of
     * the stack at this point. to think about it another way, if there is already a vm (or multiple vms!) running from
     * a local image container, instead of having a stack, we’d now have a branching effect.
     * 
     * consider this disk image stack:
     * 
     * 
     * a -> b -> c -> d
     * 
     * where ‘a’ is the current live disk image and the others are part of snapshots taken earlier.
     * 
     * now say we want to create a vm from a snapshot represented by c in that stack. once running, we now have:
     * 
     * 
     * 
     * e -----| | a -> b -> c -> d
     * 
     * 
     * where a is the live disk image for the previously running vm and e is the live disk image for the new vm. a lives
     * on b, c and d where e lives only on c and d.
     * 
     * and that’s one volume on one node.
     * 
     * we’d need to keep track of this across all local image containers associated with the vm and do so on both nodes.
     * 
     * another issue is that the vm when it starts would immediately conflict on the network with the other running vm.
     * marathon got around this by disabling all networks in the newly created vm in this circumstance.
     * 
     * 
     * clearly, this is all going to be fraught with great levels of complexity.
     * 
     * i’m estimating this as follows:
     * 
     * design: 20 days
     * 
     * ui work: 10 days
     * 
     * smd/policy: 30 days
     * 
     * spine: 30 days
     */
}
